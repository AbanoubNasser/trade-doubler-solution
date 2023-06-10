package com.services.tradedoubler.productservice.api;

import com.services.tradedoubler.productservice.SpringBootComponentTest;
import com.services.tradedoubler.productservice.api.bo.ProductsFileStatusUpdateRequest;
import com.services.tradedoubler.productservice.model.FileStatus;
import com.services.tradedoubler.productservice.model.ProductsFileEntity;
import com.services.tradedoubler.productservice.service.FileStorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.services.tradedoubler.productservice.service.FileStorageService.SEPARATOR;
import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductsFileControllerIntegrationTest extends SpringBootComponentTest {

    private final String PRODUCTS_FILE_URL_TEMPLATE = BASE_URL_TEMPLATE +"/products/files";

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    FileStorageService fileStorageService;

    @AfterEach
    void tearDown() {
        productsFileRepository.deleteAll();
        fileStorageService.deleteAll();
    }

    @Test
    public void testUploadProductsFileWithoutFile() throws Exception {
        mockMvc.perform(post(PRODUCTS_FILE_URL_TEMPLATE)
                        .contentType(MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUploadProductsFileSuccessfully() throws Exception {
        String productFileId = submitProductFileUploadRequestSuccessfully();
        Optional<ProductsFileEntity> fromRepository = productsFileRepository.findById(productFileId);
        assertTrue(fromRepository.isPresent());

        ProductsFileEntity entity = fromRepository.get();
        assertEquals( productFileId, entity.getId());
        assertEquals(FileStatus.UPLOADED, entity.getStatus());
        assertNotNull(entity.getCreatedDateTime());
        assertNotNull(entity.getModifiedDateTime());

        Resource uploadedFile = fileStorageService.loadProductsFileByName(entity.getId()+SEPARATOR+entity.getFileName());
        assertNotNull(uploadedFile);
    }

    @Test
    public void testGetProductsFileWithoutFileStatus() throws Exception {
        mockMvc.perform(get(PRODUCTS_FILE_URL_TEMPLATE)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetProductsFileWithNotValidStatus() throws Exception {
        mockMvc.perform(get(PRODUCTS_FILE_URL_TEMPLATE)
                        .param("status", "NOT_VALID_STATUS")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetProductsFileByStatusSuccessfully() throws Exception {
        ProductsFileEntity entity1 = new ProductsFileEntity("filename1");
        ProductsFileEntity entity2 = new ProductsFileEntity("filename2");
        ProductsFileEntity entity3 = new ProductsFileEntity("filename3");
        entity3.setStatus(FileStatus.IN_PROGRESS);
        productsFileRepository.saveAll(List.of(entity1, entity2, entity3));

        mockMvc.perform(get(PRODUCTS_FILE_URL_TEMPLATE)
                        .param("status", "UPLOADED")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(String.format("""
                    [
                        {
                        "id":"%s",
                        "fileName":"filename1",
                        "status":"UPLOADED",
                        "comment":null
                        },
                        {
                        "id":"%s",
                        "fileName":"filename2",
                        "status":"UPLOADED",
                        "comment":null
                        }
                    ]
                """,entity1.getId(), entity2.getId())));
    }

    @Test
    public void testUpdateProductFileStatusForNotExistEntity() throws Exception {
        ProductsFileStatusUpdateRequest request = new ProductsFileStatusUpdateRequest();
        request.setStatus(FileStatus.IN_PROGRESS);
        request.setComment("In progress");

        mockMvc.perform(put(PRODUCTS_FILE_URL_TEMPLATE+ "/"+UUID.randomUUID())
                        .content(mapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                 {"description":"Products file with specified criteria is not found","error":"NOT_FOUND_PRODUCTS_FILE","status":"NOT_FOUND"}
            """));
    }

    @Test
    public void testUpdateProductFileStatusSuccessfully() throws Exception {
        ProductsFileEntity entity = new ProductsFileEntity("fileName");
        productsFileRepository.save(entity);

        ProductsFileStatusUpdateRequest request = new ProductsFileStatusUpdateRequest();
        request.setStatus(FileStatus.IN_PROGRESS);
        request.setComment("In progress");

        mockMvc.perform(put(PRODUCTS_FILE_URL_TEMPLATE+ "/"+entity.getId())
                        .content(mapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
        ProductsFileEntity fromRepository =productsFileRepository.findById(entity.getId()).get();
        assertEquals(FileStatus.IN_PROGRESS, fromRepository.getStatus());
        assertEquals("In progress", fromRepository.getComment());
    }

    @Test
    public void testGetProductFileContentForNotExistEntity() throws Exception {
        mockMvc.perform(get(PRODUCTS_FILE_URL_TEMPLATE+ "/"+UUID.randomUUID()+"/content")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                 {"description":"Products file with specified criteria is not found","error":"NOT_FOUND_PRODUCTS_FILE","status":"NOT_FOUND"}
            """));
    }

    @Test
    public void testGetProductFileContentSuccessfully() throws Exception {
        String productFileId = submitProductFileUploadRequestSuccessfully();
        MvcResult result = mockMvc.perform(get(PRODUCTS_FILE_URL_TEMPLATE+ "/"+productFileId+"/content")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String actualResponse = result.getResponse().getContentAsString();
        String expectedResponse = getResourceFileAsString("products.xml");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetProductFileEntityForNotExistEntity() throws Exception {
        mockMvc.perform(get(PRODUCTS_FILE_URL_TEMPLATE+ "/"+UUID.randomUUID())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                 {"description":"Products file with specified criteria is not found","error":"NOT_FOUND_PRODUCTS_FILE","status":"NOT_FOUND"}
            """));
    }

    @Test
    public void testGetProductFileEntitySuccessfully() throws Exception {
        String productFileId = submitProductFileUploadRequestSuccessfully();
        mockMvc.perform(get(PRODUCTS_FILE_URL_TEMPLATE+ "/"+productFileId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(String.format("""
                {
                "id": "%s",
                "fileName":"products.xml",
                "status":"UPLOADED",
                "comment":null
                }
                """,productFileId)));
    }

    private String submitProductFileUploadRequestSuccessfully() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "products.xml", MediaType.APPLICATION_XML_VALUE, getResourceFileAsString("products.xml").getBytes());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart(PRODUCTS_FILE_URL_TEMPLATE)
                        .file(mockMultipartFile)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isCreated())
                .andExpect(header().exists(LOCATION_HEADER)).andReturn();
        String locationHeaderValue = result.getResponse().getHeader(LOCATION_HEADER);
        assertTrue(locationHeaderValue.startsWith(PRODUCTS_FILE_URL_TEMPLATE));
        return  locationHeaderValue.substring(PRODUCTS_FILE_URL_TEMPLATE.length()+1);
    }

    private String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}

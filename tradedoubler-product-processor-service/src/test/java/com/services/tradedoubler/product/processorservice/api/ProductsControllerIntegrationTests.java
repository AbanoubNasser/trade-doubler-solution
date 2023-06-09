package com.services.tradedoubler.product.processorservice.api;

import com.services.tradedoubler.product.processorservice.SpringBootComponentTest;
import com.services.tradedoubler.product.processorservice.api.bo.ExportFileType;
import com.services.tradedoubler.product.processorservice.bo.Product;
import com.services.tradedoubler.product.processorservice.repository.FieldRepository;
import com.services.tradedoubler.product.processorservice.repository.OfferRepository;
import com.services.tradedoubler.product.processorservice.repository.PriceRepository;
import com.services.tradedoubler.product.processorservice.repository.ProductImageRepository;
import com.services.tradedoubler.product.processorservice.service.ProductService;
import com.services.tradedoubler.product.processorservice.service.ProductsProcessorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductsControllerIntegrationTests extends SpringBootComponentTest {

    private final ObjectMapper mapper = new ObjectMapper();

    private final String PRODUCTS_URL_TEMPLATE = BASE_URL_TEMPLATE +"/products";

    @Autowired
    ProductsProcessorService productsProcessorService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private MockMvc mockMvc;


    @AfterEach
    void tearDown(){
        productImageRepository.deleteAll();
        fieldRepository.deleteAll();
        priceRepository.deleteAll();
        offerRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testExportProductsWithoutProductFileId() throws Exception {
        mockMvc.perform(get(PRODUCTS_URL_TEMPLATE+"/export")
                        .param("exportFileType", ExportFileType.CSV.name())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExportProductsWithoutExportFileType() throws Exception {
        mockMvc.perform(get(PRODUCTS_URL_TEMPLATE+"/export")
                        .param("productsFileId", UUID.randomUUID().toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExportProductsWithEmptyProducts() throws Exception {
        mockMvc.perform(get(PRODUCTS_URL_TEMPLATE+"/export")
                        .param("productsFileId", UUID.randomUUID().toString())
                        .param("exportFileType", ExportFileType.CSV.name())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound()).andExpect(content().json("""
                 {"description":"No products found with provided product file id","error":"NO_PRODUCTS_FOUND_FOR_PRODUCT_FILE_ID","status":"NOT_FOUND"}
                """));
    }

    @Test
    public void testExportProductsSuccessfullyWithCSV() throws Exception {
        String productFileId = UUID.randomUUID().toString();
        processProductsToDB(productFileId);
        MvcResult mvcResult = mockMvc.perform(get(PRODUCTS_URL_TEMPLATE + "/export")
                        .param("productsFileId", productFileId)
                        .param("exportFileType", ExportFileType.CSV.name())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String contentDispositionHeaderValue = mvcResult.getResponse().getHeader(HttpHeaders.CONTENT_DISPOSITION);

        assertEquals("attachment; filename=result.csv", contentDispositionHeaderValue);
    }

    @Test
    public void testExportProductsSuccessfullyWithXml() throws Exception {
        String productFileId = UUID.randomUUID().toString();
        processProductsToDB(productFileId);
        MvcResult mvcResult = mockMvc.perform(get(PRODUCTS_URL_TEMPLATE + "/export")
                        .param("productsFileId", productFileId)
                        .param("exportFileType", ExportFileType.XML.name())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String contentDispositionHeaderValue = mvcResult.getResponse().getHeader(HttpHeaders.CONTENT_DISPOSITION);

        assertEquals("attachment; filename=result.xml", contentDispositionHeaderValue);
    }

    @Test
    public void testExportProductsSuccessfullyWithJson() throws Exception {
        String productFileId = UUID.randomUUID().toString();
        processProductsToDB(productFileId);
        MvcResult mvcResult = mockMvc.perform(get(PRODUCTS_URL_TEMPLATE + "/export")
                        .param("productsFileId", productFileId)
                        .param("exportFileType", ExportFileType.JSON.name())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String contentDispositionHeaderValue = mvcResult.getResponse().getHeader(HttpHeaders.CONTENT_DISPOSITION);
        assertEquals("attachment; filename=result.json", contentDispositionHeaderValue);
    }

    private void processProductsToDB(String productFileId) throws IOException {
        String content = getResourceFileAsString("Products.xml");
        Set<Product> products = productsProcessorService.processProducts(content, productFileId);
        productService.createProducts(products);
    }
}

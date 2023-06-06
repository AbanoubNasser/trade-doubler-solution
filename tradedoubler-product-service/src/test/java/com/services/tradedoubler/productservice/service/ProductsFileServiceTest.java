package com.services.tradedoubler.productservice.service;

import com.services.tradedoubler.productservice.api.bo.ProductsFileDto;
import com.services.tradedoubler.productservice.api.bo.ProductsFileStatusUpdateRequest;
import com.services.tradedoubler.productservice.exception.ServiceException;
import com.services.tradedoubler.productservice.model.FileStatus;
import com.services.tradedoubler.productservice.model.ProductsFileEntity;
import com.services.tradedoubler.productservice.repository.ProductsFileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class ProductsFileServiceTest {

    @Mock
    ProductsFileRepository productsFileRepository;
    @Mock
    FileStorageService fileStorageService;
    @InjectMocks
    ProductsFileService productsFileService;

    @Test
    public void testUploadProductsFileWithException(){
        doThrow(ServiceException.class).when(fileStorageService).addProductsFileToBucket(any(), any());
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "products.xml", MediaType.APPLICATION_XML_VALUE, "<data>value</data>".getBytes());
        assertThrows(ServiceException.class, ()->productsFileService.uploadProductFile(mockMultipartFile));
        verify(productsFileRepository, times(0)).save(any());
    }

    @Test
    public void testUploadProductsFileSuccessfully(){
        doNothing().when(fileStorageService).addProductsFileToBucket(any(), any());
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "products.xml", MediaType.APPLICATION_XML_VALUE, "<data>value</data>".getBytes());
        productsFileService.uploadProductFile(mockMultipartFile);
        verify(productsFileRepository, times(1)).save(any());
    }

    @Test
    public void testGetProductsFileByStatusWithEmptyResult(){
       when(productsFileRepository.findByStatus(any())).thenReturn(List.of());
       List<ProductsFileDto> result = productsFileService.getProductsFilesByStatus(FileStatus.UPLOADED);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetProductsFileByStatusSuccessfully(){
        when(productsFileRepository.findByStatus(any()))
                .thenReturn(List.of(ProductsFileEntity.builder().id("id").fileName("test.xml").status(FileStatus.UPLOADED).build()));
        List<ProductsFileDto> result = productsFileService.getProductsFilesByStatus(FileStatus.UPLOADED);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("id", result.get(0).getId());
        assertEquals("test.xml", result.get(0).getFileName());
        assertEquals(FileStatus.UPLOADED, result.get(0).getStatus());
    }


    @Test
    public void testUpdateProductsFileStatusForNotExistEntity(){
        when(productsFileRepository.findById(any())).thenThrow(ServiceException.class);
        ProductsFileStatusUpdateRequest request = new ProductsFileStatusUpdateRequest();
        request.setStatus(FileStatus.IN_PROGRESS);
        request.setComment("In progress");
        assertThrows(ServiceException.class, ()->productsFileService.updateProductsFileStatus(UUID.randomUUID().toString(),request));
    }

    @Test
    public void testUpdateProductsFileStatusSuccessfully(){
        String productFileId = UUID.randomUUID().toString();
        when(productsFileRepository.findById(any())).thenReturn(Optional.of(ProductsFileEntity.builder().id(productFileId).fileName("text.xml").status(FileStatus.UPLOADED).build()));
        ProductsFileStatusUpdateRequest request = new ProductsFileStatusUpdateRequest();
        request.setStatus(FileStatus.IN_PROGRESS);
        request.setComment("In progress");
        productsFileService.updateProductsFileStatus(productFileId,request);
        verify(productsFileRepository, times(1)).findById(productFileId);
        verify(productsFileRepository, times(1)).save(any());
    }

    @Test
    public void testGetProductsFileContentWithFailure(){
        when(productsFileRepository.findById(any())).thenReturn(Optional.of(ProductsFileEntity.builder().id(UUID.randomUUID().toString()).fileName("text.xml").status(FileStatus.UPLOADED).build()));
        when(fileStorageService.loadProductsFileByName(any())).thenThrow(ServiceException.class);
        assertThrows(ServiceException.class, ()->productsFileService.getProductsFileContent(UUID.randomUUID().toString()));
    }

}

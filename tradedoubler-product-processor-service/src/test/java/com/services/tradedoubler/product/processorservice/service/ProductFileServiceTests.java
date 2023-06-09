package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.BaseComponentTest;
import com.services.tradedoubler.product.processorservice.exception.ServiceException;
import com.services.tradedoubler.product.processorservice.integration.ProductsFileClient;
import com.services.tradedoubler.product.processorservice.integration.data.FileStatus;
import com.services.tradedoubler.product.processorservice.integration.data.ProductsFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductFileServiceTests extends BaseComponentTest {

    @Mock
    private ProductsFileClient productsFileClient;

    @InjectMocks
    private ProductsFileService productsFileService;


    @Test
    void testUpdateProductsFileStatusSuccessfully(){
        doNothing().when(productsFileClient).updateProcessedProductsFileStatus(any(), any());
        assertDoesNotThrow(()-> productsFileService.updateProductsFileStatus(UUID.randomUUID().toString(), FileStatus.FAILED_TO_PROCESS, "comment"));
    }

    @Test
    void testUpdateProductsFileStatusWithException(){
        doThrow(RuntimeException.class).when(productsFileClient).updateProcessedProductsFileStatus(any(), any());
        assertThrows(ServiceException.class, ()-> productsFileService.updateProductsFileStatus(UUID.randomUUID().toString(), FileStatus.FAILED_TO_PROCESS, "comment"));
    }

    @Test
    void testUGetProductsFilesStatusSuccessfully(){
        when(productsFileClient.getProductsFilesByStatus(any())).thenReturn(List.of(new ProductsFile()));
       List<ProductsFile> productsFiles = productsFileService.getProductsFilesByStatus(FileStatus.UPLOADED);
       assertNotNull(productsFiles);
       assertEquals(1, productsFiles.size());
    }

    @Test
    void testGetProductsFilesStatusWithException(){
        when(productsFileClient.getProductsFilesByStatus(any())).thenThrow(RuntimeException.class);
        assertThrows(ServiceException.class, ()-> productsFileService.getProductsFilesByStatus(FileStatus.UPLOADED));
    }
    @Test
    void testGetProductsFileContentSuccessfully(){
        when(productsFileClient.getProductsFileContent(any())).thenReturn("<Test>data</Test>");
        String content = productsFileService.getProductsFileContent(UUID.randomUUID().toString());
        assertNotNull(content);
    }

    @Test
    void testGetProductsFileContentWithException(){
        when(productsFileClient.getProductsFileContent(any())).thenThrow(RuntimeException.class);
        assertThrows(ServiceException.class, ()-> productsFileService.getProductsFileContent(UUID.randomUUID().toString()));
    }


}

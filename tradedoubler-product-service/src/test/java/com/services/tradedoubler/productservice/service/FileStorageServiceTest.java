package com.services.tradedoubler.productservice.service;

import com.services.tradedoubler.productservice.config.FileStorageProperties;
import com.services.tradedoubler.productservice.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class FileStorageServiceTest {

    private FileStorageProperties fileStorageProperties;
    private FileStorageService fileStorageService ;

    @BeforeEach
    void init(){
        fileStorageProperties = new FileStorageProperties();
        fileStorageProperties.setBucketName("product-files-uploads");
        fileStorageService = new FileStorageService(fileStorageProperties);
    }
    @Test
    public void testInitBucketWithException() {
        try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
            utilities.when(() -> Files.createDirectories(any())).thenThrow(IOException.class);
            assertThrows(ServiceException.class, ()-> fileStorageService.initProductsFileBucket());
        }
    }

    @Test
    public void testDeleteFilesFromBucketWithException() {
        try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
            utilities.when(() -> Files.newDirectoryStream(any())).thenThrow(IOException.class);
            assertThrows(ServiceException.class, ()-> fileStorageService.deleteAll());
        }
    }

    @Test
    public void testAddProductsFileToBucketWithException(){
        assertThrows(ServiceException.class, ()-> fileStorageService.addProductsFileToBucket(UUID.randomUUID().toString(),null ));
    }

    @Test
    public void testLoadProductsFileFromBucketWithException(){
        assertThrows(ServiceException.class, ()-> fileStorageService.loadProductsFileByName("randomName"));
    }
}

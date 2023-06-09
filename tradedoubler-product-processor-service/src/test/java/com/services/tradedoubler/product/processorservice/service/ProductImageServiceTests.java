package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.BaseComponentTest;
import com.services.tradedoubler.product.processorservice.bo.ProductImage;
import com.services.tradedoubler.product.processorservice.exception.ServiceException;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import com.services.tradedoubler.product.processorservice.model.ProductImageEntity;
import com.services.tradedoubler.product.processorservice.repository.ProductImageRepository;
import com.services.tradedoubler.product.processorservice.service.mapper.ProductImageMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductImageServiceTests extends BaseComponentTest {

    @Mock
    private  ProductImageRepository productImageRepository;

    @Mock
    private ProductImageMapper productImageMapper;

    @InjectMocks
    private ProductImageService productImageService;

    @Test
    void testCreateProductImageSuccessfully() {
        when(productImageMapper.map(any())).thenReturn(ProductImageEntity.builder().value("ImageValue").height(50).width(50).build());
        when(productImageRepository.save(any())).thenReturn(ProductImageEntity.builder().id(UUID.randomUUID()).value("ImageValue").height(50).width(50).build());
        assertDoesNotThrow(()-> productImageService.createProductImage(ProductEntity.builder().id(UUID.randomUUID()).name("productName").build(), buildProductImage()));
    }

    @Test
    void testGetProductImageWithEmpty() {
        when(productImageRepository.findByProductId(any())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, ()-> productImageService.getProductImageEntity(UUID.randomUUID()));
    }

    @Test
    void testGetProductImageSuccessfully() {
        when(productImageRepository.findByProductId(any())).thenReturn(Optional.of(ProductImageEntity.builder().id(UUID.randomUUID()).value("ImageValue").build()));
        assertDoesNotThrow(()-> productImageService.getProductImageEntity(UUID.randomUUID()));
    }

    private ProductImage buildProductImage() throws IOException {
        return getProduct().getProductImage();
    }

}

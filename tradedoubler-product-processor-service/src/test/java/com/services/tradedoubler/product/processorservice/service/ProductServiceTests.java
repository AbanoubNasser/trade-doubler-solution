package com.services.tradedoubler.product.processorservice.service;


import com.services.tradedoubler.product.processorservice.BaseComponentTest;
import com.services.tradedoubler.product.processorservice.api.bo.ProductDto;
import com.services.tradedoubler.product.processorservice.bo.Product;
import com.services.tradedoubler.product.processorservice.bo.Result;
import com.services.tradedoubler.product.processorservice.exception.ServiceException;
import com.services.tradedoubler.product.processorservice.model.*;
import com.services.tradedoubler.product.processorservice.repository.ProductRepository;
import com.services.tradedoubler.product.processorservice.service.mapper.ProductMapper;
import com.services.tradedoubler.product.processorservice.utils.XmlUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests extends BaseComponentTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private OfferService offerService;
    @Mock
    private ProductImageService productImageService;
    @Mock
    private FieldService fieldService;
    @InjectMocks
    ProductService productService;
    private XmlUtility xmlUtility;

    @BeforeEach
    void init(){
        xmlUtility = new XmlUtility();
    }

    @Test
    void testCreateProductsWithExceptionWhileMapping(){
        when(productMapper.mapToSet(any())).thenThrow(RuntimeException.class);
        assertThrows(ServiceException.class, ()->productService.createProducts(buildMappedProducts()));
    }

    @Test
    void testCreateProductsSuccessfully() throws IOException {
        when(productMapper.mapToSet(any())).thenReturn(buildProductsEntitySet());
        when(productRepository.saveAll(any())).thenReturn(buildProductsEntitySet().stream().toList());
        doNothing().when(offerService).createProductOffers(any(), any());
        doNothing().when(productImageService).createProductImage(any(), any());
        doNothing().when(fieldService).creatProductFields(any(), any());
        productService.createProducts(buildMappedProducts());
        verify(offerService, times(3)).createProductOffers(any(), any());
        verify(productImageService, times(3)).createProductImage(any(), any());
        verify(fieldService, times(3)).creatProductFields(any(), any());
    }

    @Test
    void testGetProductsByProductsFileIdWIthEmptyResponse(){
        when(productRepository.findByProductFileId(any())).thenReturn(List.of());
        assertDoesNotThrow(()-> productService.getProductsByProductsFileId(UUID.randomUUID().toString()));
    }

    @Test
    void testGetProductsByProductsFileIdWIthException(){
        when(productRepository.findByProductFileId(any())).thenReturn(buildProductsEntitySet().stream().toList());
        when(productImageService.getProductImageEntity(any())).thenThrow(ServiceException.class);
        assertThrows(ServiceException.class, ()-> productService.getProductsByProductsFileId(UUID.randomUUID().toString()));
    }

    @Test
    void testGetProductsByProductsFileIdSuccessfully(){
        when(productRepository.findByProductFileId(any())).thenReturn(buildProductsEntitySet().stream().toList());
        when(productImageService.getProductImageEntity(any()))
                .thenReturn(ProductImageEntity.builder().id(UUID.randomUUID()).value("ImageValue").build());
        when(offerService.getProductOffers(any()))
                .thenReturn(Map.of(OfferEntity.builder().id(UUID.randomUUID()).availability("available").build(),
                        PriceEntity.builder().value(BigDecimal.valueOf(50)).build()));
        when(fieldService.getProductFields(any()))
                .thenReturn(List.of(FieldEntity.builder().id(UUID.randomUUID()).name("FieldName").value("FieldValue").build()));

        Set<ProductDto> products = productService.getProductsByProductsFileId(UUID.randomUUID().toString());
        assertNotNull(products);
        assertEquals(3, products.size());
    }


    private Set<ProductEntity> buildProductsEntitySet(){
        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setId(UUID.randomUUID());
        productEntity1.setName("First Test product name");
        productEntity1.setGroupingId("skuSKU1-TV000001");

        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setId(UUID.randomUUID());
        productEntity2.setName("Second Test product name");
        productEntity2.setGroupingId("skuSKU1-TV0000011");

        ProductEntity productEntity3 = new ProductEntity();
        productEntity3.setId(UUID.randomUUID());
        productEntity3.setName("Third Test product name");
        productEntity3.setGroupingId("skuSKU1-TV0000012");
        return Set.of(productEntity1, productEntity2, productEntity3);
    }
}

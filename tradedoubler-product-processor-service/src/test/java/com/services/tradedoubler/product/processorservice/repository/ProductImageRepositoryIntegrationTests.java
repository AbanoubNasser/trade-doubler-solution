package com.services.tradedoubler.product.processorservice.repository;

import com.services.tradedoubler.product.processorservice.SpringBootComponentTest;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import com.services.tradedoubler.product.processorservice.model.ProductImageEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductImageRepositoryIntegrationTests extends SpringBootComponentTest {

    @Autowired
    private ProductImageRepository productImageRepository;

    @AfterEach
    void tearDown() {
        productImageRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void testSaveAndRetrieveProductImage() {

        ProductEntity product = new ProductEntity();
        product.setName("ProductTest");
        ProductEntity productEntityFromRepo = productRepository.save(product);

        ProductImageEntity productImageEntity = new ProductImageEntity();
        productImageEntity.setProduct(productEntityFromRepo);
        productImageEntity.setValue("testImage");

        ProductImageEntity productImageEntityFromRepo = productImageRepository.save(productImageEntity);

        Optional<ProductImageEntity> fromRepoOptional = productImageRepository.findById(productImageEntityFromRepo.getId());
        assertTrue(fromRepoOptional.isPresent());
        assertEquals(productEntityFromRepo.getId(), fromRepoOptional.get().getProduct().getId());
        assertEquals("testImage", fromRepoOptional.get().getValue());
    }

    @Test
    void testRetrieveProductsImageByProductId() {

        ProductEntity product = new ProductEntity();
        product.setName("ProductTest");

        ProductEntity productEntityFromRepo = productRepository.save(product);
        ProductImageEntity productImageEntity = new ProductImageEntity();
        productImageEntity.setProduct(productEntityFromRepo);
        productImageEntity.setValue("testImage");

        ProductImageEntity productImageEntityFromRepo = productImageRepository.save(productImageEntity);

        Optional<ProductImageEntity> fromRepoOptional = productImageRepository.findByProductId(productEntityFromRepo.getId());
        assertTrue(fromRepoOptional.isPresent());
        assertEquals(productEntityFromRepo.getId(), fromRepoOptional.get().getProduct().getId());
        assertEquals("testImage", fromRepoOptional.get().getValue());
    }
}

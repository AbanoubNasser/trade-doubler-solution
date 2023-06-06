package com.services.tradedoubler.productservice.repository;

import com.services.tradedoubler.productservice.SpringBootComponentTest;
import com.services.tradedoubler.productservice.model.FileStatus;
import com.services.tradedoubler.productservice.model.ProductsFileEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ProductsFileRepositoryIntegrationTest extends SpringBootComponentTest {

    @AfterEach
    void tearDown() {
        productsFileRepository.deleteAll();
    }

    @Test
    void saveAndRetrieveProductsFile() {
        ProductsFileEntity entity = productsFileRepository.save(new ProductsFileEntity("fileName"));
        Optional<ProductsFileEntity> fromRepository = productsFileRepository.findById(entity.getId());
        assertTrue(fromRepository.isPresent());
        assertEquals( entity, fromRepository.get());
        assertEquals( "fileName", fromRepository.get().getFileName());
        assertEquals(FileStatus.UPLOADED, fromRepository.get().getStatus());
    }

    @Test
    void retrieveProductsFile_withStatus() {
        ProductsFileEntity entity1 = new ProductsFileEntity("fileName");
        ProductsFileEntity entity2 = new ProductsFileEntity("fileName2");
        entity2.setStatus(FileStatus.IN_PROGRESS);

        ProductsFileEntity entity3 = new ProductsFileEntity("fileName3");
        productsFileRepository.saveAll(List.of(entity1, entity2, entity3));


       List<ProductsFileEntity> fromRepository = productsFileRepository.findByStatus(FileStatus.UPLOADED);
        assertNotNull(fromRepository);
        assertEquals( 2, fromRepository.size());
        fromRepository.stream().forEach(elem ->{
            assertEquals( FileStatus.UPLOADED, elem.getStatus());
        });
    }
}

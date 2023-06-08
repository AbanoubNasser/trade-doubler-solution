package com.services.tradedoubler.product.processorservice.repository;

import com.services.tradedoubler.product.processorservice.SpringBootComponentTest;
import com.services.tradedoubler.product.processorservice.model.FieldEntity;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class FieldsRepositoryIntegrationTests extends SpringBootComponentTest {

    @Autowired
    FieldRepository fieldRepository;

    @AfterEach
    void tearDown() {
        fieldRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void testSaveAndRetrieveProductFields() {
        ProductEntity product = new ProductEntity();
        product.setName("ProductTest");
        ProductEntity productEntityFromRepo = productRepository.save(product);

        FieldEntity field1 = new FieldEntity();
        field1.setName("field1Name");
        field1.setValue("field1Value");
        field1.setProduct(productEntityFromRepo);

        FieldEntity fieldEntityFromRepo = fieldRepository.save(field1);

        Optional<FieldEntity> fieldEntityFromRepoOpt = fieldRepository.findById(fieldEntityFromRepo.getId());
        assertTrue(fieldEntityFromRepoOpt.isPresent());
        assertEquals("field1Name", fieldEntityFromRepoOpt.get().getName());
        assertEquals("field1Value", fieldEntityFromRepoOpt.get().getValue());
    }

    @Test
    void testRetrieveProductFieldsByProductId() {
        ProductEntity product = new ProductEntity();
        product.setName("ProductTest");
        ProductEntity productEntityFromRepo = productRepository.save(product);

        FieldEntity field1 = new FieldEntity();
        field1.setName("field1Name");
        field1.setValue("field1Value");
        field1.setProduct(productEntityFromRepo);

        FieldEntity field2 = new FieldEntity();
        field2.setName("field2Name");
        field2.setValue("field2Value");
        field2.setProduct(productEntityFromRepo);

        fieldRepository.saveAll(List.of(field1, field2));

        List<FieldEntity> fieldEntityList = fieldRepository.findByProductId(productEntityFromRepo.getId());
        assertNotNull(fieldEntityList);
        assertEquals(2, fieldEntityList.size());
    }

}

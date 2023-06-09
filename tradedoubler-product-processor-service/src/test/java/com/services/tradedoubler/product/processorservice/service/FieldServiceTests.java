package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.BaseComponentTest;
import com.services.tradedoubler.product.processorservice.bo.Field;
import com.services.tradedoubler.product.processorservice.model.FieldEntity;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import com.services.tradedoubler.product.processorservice.repository.FieldRepository;
import com.services.tradedoubler.product.processorservice.service.mapper.FieldMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FieldServiceTests extends BaseComponentTest {

    @Mock
    private  FieldRepository fieldRepository;

    @Mock
    private FieldMapper fieldMapper;

    @InjectMocks
    private FieldService fieldService;

    @Test
    void testCreateProductsOffersSuccessfully() {
        when(fieldMapper.mapToSet(any())).thenReturn(buildFieldsEntitySet());
        when(fieldRepository.saveAll(any())).thenReturn(buildFieldsEntitySet().stream().toList());
        assertDoesNotThrow(()-> fieldService.creatProductFields(ProductEntity.builder().id(UUID.randomUUID()).name("productName").build(),buildSetOfField()));
    }

    @Test
    void testGetProductsFields(){
        when(fieldRepository.findByProductId(any())).thenReturn(buildFieldsEntitySet().stream().toList());
        fieldService.getProductFields(UUID.randomUUID());
        verify(fieldRepository, times(1)).findByProductId(any());
    }

    private Set<Field> buildSetOfField() throws IOException {
        return getProduct().getFields();
    }

    private Set<FieldEntity> buildFieldsEntitySet(){
        return Set.of(FieldEntity.builder().id(UUID.randomUUID()).name("Company").value("Samsung").build(),
                FieldEntity.builder().id(UUID.randomUUID()).name("ProductType").value("Mobile").build(),
                FieldEntity.builder().id(UUID.randomUUID()).name("HomeDelivery").value("Yes").build());
    }
}

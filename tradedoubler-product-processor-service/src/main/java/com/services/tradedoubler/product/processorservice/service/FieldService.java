package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.bo.Field;
import com.services.tradedoubler.product.processorservice.model.FieldEntity;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import com.services.tradedoubler.product.processorservice.repository.FieldRepository;
import com.services.tradedoubler.product.processorservice.service.mapper.FieldMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FieldService {

    private  final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;

    public FieldService(final FieldRepository fieldRepository, final FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
    }

    public void creatProductFields(final ProductEntity productEntity, final Set<Field> fields){
        Set<FieldEntity> fieldEntities= fieldMapper.mapToSet(fields);
        fieldEntities.stream().forEach(fieldEntity -> {
            fieldEntity.setProduct(productEntity);
        });
        fieldRepository.saveAll(fieldEntities);
    }
}

package com.services.tradedoubler.product.processorservice.service.mapper;

import com.services.tradedoubler.product.processorservice.bo.Field;
import com.services.tradedoubler.product.processorservice.model.FieldEntity;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface FieldMapper {

    FieldEntity map(Field field);
    Set<FieldEntity> mapToSet(Set<Field> fields);
}

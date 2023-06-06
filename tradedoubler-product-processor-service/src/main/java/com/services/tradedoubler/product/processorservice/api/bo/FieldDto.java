package com.services.tradedoubler.product.processorservice.api.bo;

import com.services.tradedoubler.product.processorservice.model.FieldEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
public class FieldDto {

    private UUID id;

    private String value;

    private String name;

    public static Set<FieldDto> mapToDto(List<FieldEntity> fieldEntityList){
        return fieldEntityList.stream().map(entry -> mapToDto(entry)).collect(Collectors.toSet());
    }

    private static FieldDto mapToDto(FieldEntity fieldEntity){
        return FieldDto.builder()
                .id(fieldEntity.getId())
                .value(fieldEntity.getValue())
                .name(fieldEntity.getName())
                .build();

    }
}

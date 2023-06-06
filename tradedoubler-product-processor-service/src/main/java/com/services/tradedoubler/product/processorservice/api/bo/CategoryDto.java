package com.services.tradedoubler.product.processorservice.api.bo;

import com.services.tradedoubler.product.processorservice.model.CategoryEntity;
import com.services.tradedoubler.product.processorservice.model.FieldEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
public class CategoryDto {

    private UUID id;

    private Integer categoryId;

    private String name;

    private String tdCategoryName;


    public static Set<CategoryDto> mapToDto(Set<CategoryEntity> categoryEntitySet){
        return categoryEntitySet.stream().map(entry -> mapToDto(entry)).collect(Collectors.toSet());
    }

    private static CategoryDto mapToDto(CategoryEntity categoryEntity){
        return CategoryDto.builder()
                .id(categoryEntity.getId())
                .categoryId(categoryEntity.getCategoryId())
                .name(categoryEntity.getName())
                .tdCategoryName(categoryEntity.getTdCategoryName())
                .build();

    }
}

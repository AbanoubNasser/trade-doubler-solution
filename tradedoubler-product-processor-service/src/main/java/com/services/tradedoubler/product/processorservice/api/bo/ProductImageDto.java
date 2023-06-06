package com.services.tradedoubler.product.processorservice.api.bo;

import com.services.tradedoubler.product.processorservice.model.ProductImageEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProductImageDto {

    private UUID id;

    private String value;

    private Integer height;

    private Integer width;

    public static  ProductImageDto mapToDto(ProductImageEntity productImageEntity){
        return ProductImageDto.builder()
                .id(productImageEntity.getId())
                .value(productImageEntity.getValue())
                .height(productImageEntity.getHeight())
                .width(productImageEntity.getWidth())
                .build();
    }
}

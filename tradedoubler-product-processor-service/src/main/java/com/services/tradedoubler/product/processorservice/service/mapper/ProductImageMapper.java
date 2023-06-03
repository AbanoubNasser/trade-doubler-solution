package com.services.tradedoubler.product.processorservice.service.mapper;

import com.services.tradedoubler.product.processorservice.bo.ProductImage;
import com.services.tradedoubler.product.processorservice.model.ProductImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    ProductImageEntity map(ProductImage productImage);
}

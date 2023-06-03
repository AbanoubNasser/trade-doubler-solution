package com.services.tradedoubler.product.processorservice.service.mapper;

import com.services.tradedoubler.product.processorservice.bo.Product;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import org.mapstruct.Mapper;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class, CategoryMapper.class, OfferMapper.class})
public interface ProductMapper {

    ProductEntity mapToEntity(Product product);

    Set<ProductEntity> mapToSet(Set<Product> products);
}

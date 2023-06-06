package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.bo.ProductImage;
import com.services.tradedoubler.product.processorservice.exception.ServiceError;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import com.services.tradedoubler.product.processorservice.model.ProductImageEntity;
import com.services.tradedoubler.product.processorservice.repository.ProductImageRepository;
import com.services.tradedoubler.product.processorservice.service.mapper.ProductImageMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    private final ProductImageMapper productImageMapper;

    public ProductImageService(ProductImageRepository productImageRepository, ProductImageMapper productImageMapper) {
        this.productImageRepository = productImageRepository;
        this.productImageMapper = productImageMapper;
    }

    public void createProductImage(final ProductEntity productEntity, final ProductImage productImage){
        ProductImageEntity productImageEntity = productImageMapper.map(productImage);
        productImageEntity.setProduct(productEntity);
        productImageRepository.save(productImageEntity);
    }

    public ProductImageEntity getProductImageEntity(final UUID productId){
        return productImageRepository.findByProductId(productId)
                .orElseThrow(() -> ServiceError.NOT_FOUND_PRODUCT_IMAGE.buildException());
    }
}

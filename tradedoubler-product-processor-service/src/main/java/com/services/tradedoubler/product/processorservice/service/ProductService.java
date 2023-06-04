package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.bo.Product;
import com.services.tradedoubler.product.processorservice.exception.ServiceError;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import com.services.tradedoubler.product.processorservice.repository.ProductRepository;
import com.services.tradedoubler.product.processorservice.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final OfferService offerService;
    private final ProductImageService productImageService;

    private final FieldService fieldService;

    public ProductService(final ProductRepository productRepository, final ProductMapper productMapper, final OfferService offerService, final ProductImageService productImageService, final FieldService fieldService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.offerService = offerService;
        this.productImageService = productImageService;
        this.fieldService = fieldService;
    }

    public void createProducts(Set<Product> products){
        try{
            Set<ProductEntity> productEntities = productMapper.mapToSet(products);
            List<ProductEntity> persistedProducts = productRepository.saveAll(productEntities);
            persistedProducts.stream().forEach(productEntity -> {
                Product targetProduct = products.stream().filter(product -> product.getGroupingId().equals(productEntity.getGroupingId())).findFirst().get();
                offerService.createProductOffers(productEntity, targetProduct.getOffers());
                productImageService.createProductImage(productEntity, targetProduct.getProductImage());
                fieldService.creatProductFields(productEntity, targetProduct.getFields());
            });
        }catch (Exception exception){
            throw ServiceError.ERROR_WHILE_PERSISTING_PRODUCTS_DATA.buildException(exception.getMessage());
        }
    }
}

package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.api.bo.ProductDto;
import com.services.tradedoubler.product.processorservice.bo.Product;
import com.services.tradedoubler.product.processorservice.exception.ServiceError;
import com.services.tradedoubler.product.processorservice.model.*;
import com.services.tradedoubler.product.processorservice.repository.ProductRepository;
import com.services.tradedoubler.product.processorservice.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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

    @Transactional
    public void createProducts(Set<Product> products) {
        try {
            Set<ProductEntity> productEntities = productMapper.mapToSet(products);
            List<ProductEntity> persistedProducts = productRepository.saveAll(productEntities);
            persistedProducts.stream().forEach(productEntity -> {
                Product targetProduct = products.stream().filter(product -> product.getGroupingId().equals(productEntity.getGroupingId())).findFirst().get();
                offerService.createProductOffers(productEntity, targetProduct.getOffers());
                productImageService.createProductImage(productEntity, targetProduct.getProductImage());
                fieldService.creatProductFields(productEntity, targetProduct.getFields());
            });
        } catch (Exception exception) {
            throw ServiceError.ERROR_WHILE_PERSISTING_PRODUCTS_DATA.buildException(exception.getMessage());
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    public Set<ProductDto> getProductsByProductsFileId(final String productFileId) {
        List<ProductEntity> products = productRepository.findByProductFileId(productFileId);
        return products.parallelStream().map(product -> getProductDto(product)).collect(Collectors.toSet());
    }

    private ProductDto getProductDto(ProductEntity product) {
        try {
            CompletableFuture<ProductImageEntity> productImageEntityFuture =
                    CompletableFuture.supplyAsync(() -> productImageService.getProductImageEntity(product.getId()));
            CompletableFuture<List<FieldEntity>> productsFieldsFuture =
                    CompletableFuture.supplyAsync(() -> fieldService.getProductFields(product.getId()));
            CompletableFuture<Map<OfferEntity, PriceEntity>> productOfferPricesFuture =
                    CompletableFuture.supplyAsync(() -> offerService.getProductOffers(product.getId()));
            CompletableFuture.allOf(productImageEntityFuture, productsFieldsFuture, productOfferPricesFuture).get();
            return ProductDto.mapToDto(product, productImageEntityFuture.join(), productsFieldsFuture.join(), productOfferPricesFuture.join());
        } catch (ExecutionException | InterruptedException e) {
            throw ServiceError.INTERNAL_SERVER_ERROR.buildException(e.getMessage());
        }
    }
}

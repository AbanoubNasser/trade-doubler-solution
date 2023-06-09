package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.api.bo.ExportFileType;
import com.services.tradedoubler.product.processorservice.api.bo.ProductDto;
import com.services.tradedoubler.product.processorservice.api.bo.Result;
import com.services.tradedoubler.product.processorservice.exception.ServiceError;
import com.services.tradedoubler.product.processorservice.exception.ServiceException;
import com.services.tradedoubler.product.processorservice.service.strategy.ExportStrategyFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProductsExportService {
    private final ExportStrategyFactory exportStrategyFactory;
    private final ProductService productService;

    public ProductsExportService(ExportStrategyFactory exportStrategyFactory, ProductService productService) {
        this.exportStrategyFactory = exportStrategyFactory;
        this.productService = productService;
    }

    public ByteArrayResource exportProducts(final String productFileId, final ExportFileType exportFileType){
        Set<ProductDto> productsByProductsFileId = productService.getProductsByProductsFileId(productFileId);
        if(productsByProductsFileId.size() >0){
            Result resultObject = Result.builder().products(productsByProductsFileId).build();
            byte[] result = exportStrategyFactory.findExportStrategyByExportFileType(exportFileType).exportData(resultObject);
            return new ByteArrayResource(result);
        }
        throw ServiceError.NO_PRODUCTS_FOUND_FOR_PRODUCT_FILE_ID.buildException();
    }
}

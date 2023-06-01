package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.integration.ProductsFileClient;
import com.services.tradedoubler.product.processorservice.integration.data.FileStatus;
import com.services.tradedoubler.product.processorservice.integration.data.ProductsFile;
import com.services.tradedoubler.product.processorservice.integration.data.ProductsFileStatusUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsFileService {

    private final ProductsFileClient productsFileClient;

    public ProductsFileService(ProductsFileClient productsFileClient) {
        this.productsFileClient = productsFileClient;
    }

    public void updateProductsFileStatus(final String fileId, final FileStatus status, final String comment){
        ProductsFileStatusUpdateRequest request = ProductsFileStatusUpdateRequest
                .builder()
                .status(status)
                .comment(comment)
                .build();
        productsFileClient.updateProcessedProductsFileStatus(fileId, request);
    }
    public List<ProductsFile> getProductsFilesByStatus(final FileStatus fileStatus){
        return productsFileClient.getProductsFilesByStatus(fileStatus.name().toUpperCase());
    }

    public String getProductsFileContent(final String fileId){
        return productsFileClient.getProductsFileContent(fileId);
    }
}

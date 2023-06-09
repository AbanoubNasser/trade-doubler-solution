package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.exception.ServiceError;
import com.services.tradedoubler.product.processorservice.integration.ProductsFileClient;
import com.services.tradedoubler.product.processorservice.integration.data.FileStatus;
import com.services.tradedoubler.product.processorservice.integration.data.ProductsFile;
import com.services.tradedoubler.product.processorservice.integration.data.ProductsFileStatusUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductsFileService {

    private final ProductsFileClient productsFileClient;

    public ProductsFileService(ProductsFileClient productsFileClient) {
        this.productsFileClient = productsFileClient;
    }

    public void updateProductsFileStatus(final String fileId, final FileStatus status, final String comment){
        try{
            ProductsFileStatusUpdateRequest request = ProductsFileStatusUpdateRequest
                    .builder()
                    .status(status)
                    .comment(comment)
                    .build();
            productsFileClient.updateProcessedProductsFileStatus(fileId, request);
        }catch (Exception ex){
            log.error("Error while update products file status due to {}", ex.getMessage());
            throw ServiceError.INTERNAL_SERVER_ERROR.buildException(ex.getMessage());
        }
    }
    public List<ProductsFile> getProductsFilesByStatus(final FileStatus fileStatus){
        try {
            return productsFileClient.getProductsFilesByStatus(fileStatus.name().toUpperCase());
        }catch (Exception ex){
            log.error("Error while retrieving products files due to {}", ex.getMessage());
            throw ServiceError.INTERNAL_SERVER_ERROR.buildException(ex.getMessage());
        }

    }

    public String getProductsFileContent(final String fileId){
        try {
            return productsFileClient.getProductsFileContent(fileId);
        }catch (Exception ex){
            log.error("Error while retrieve product file {} content due to {}", fileId ,ex.getMessage());
            throw ServiceError.INTERNAL_SERVER_ERROR.buildException(ex.getMessage());
        }
    }
}

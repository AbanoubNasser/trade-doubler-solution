package com.services.tradedoubler.productservice.service;

import com.services.tradedoubler.productservice.api.bo.ProductsFileStatusUpdateRequest;
import com.services.tradedoubler.productservice.exception.ServiceError;
import com.services.tradedoubler.productservice.model.ProductsFileEntity;
import com.services.tradedoubler.productservice.repository.ProductsFileRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
@Slf4j
public class ProductsFileService {

    private final ProductsFileRepository productsFileRepository;
    private final FileStorageService fileStorageService;

    public ProductsFileService(ProductsFileRepository productsFileRepository, FileStorageService fileStorageService) {
        this.productsFileRepository = productsFileRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public ProductsFileEntity uploadProductFile(final MultipartFile file){
        log.info("Start upload product file {}", file.getOriginalFilename());
        fileStorageService.addProductsFileToBucket(file);
        log.info("product file {} uploaded successfully", file.getOriginalFilename());
        ProductsFileEntity entity = new ProductsFileEntity(file.getOriginalFilename());
        return productsFileRepository.save(entity);
    }

    public ProductsFileEntity updateProductsFileStatus(final String fileId, final ProductsFileStatusUpdateRequest productsFileStatusUpdateRequest) {
        log.info("Updating product file with id {} status", fileId);
        ProductsFileEntity entity = getProductsFileEntityById(fileId);
        entity.setStatus(productsFileStatusUpdateRequest.getStatus());
        entity.setComment(productsFileStatusUpdateRequest.getComment());
        return productsFileRepository.save(entity);
    }

    public String getProductsFileContent(final String fileId) {
        log.info("Retrieve product file with id {} content", fileId);
        ProductsFileEntity entity = getProductsFileEntityById(fileId);
        try {
            return fileStorageService.loadProductsFileByName(entity.getFileName()).getContentAsString(Charset.defaultCharset());
        } catch (IOException e) {
           throw ServiceError.INTERNAL_SERVER_ERROR.buildException(e.getMessage());
        }
    }

    private  ProductsFileEntity getProductsFileEntityById(final String fileId){
       return productsFileRepository.findById(fileId).orElseThrow(()-> ServiceError.NOT_FOUND_PRODUCTS_FILE.buildException());
    }

}

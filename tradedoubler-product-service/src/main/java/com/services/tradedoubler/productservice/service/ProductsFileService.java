package com.services.tradedoubler.productservice.service;

import com.services.tradedoubler.productservice.api.bo.ProductsFileDto;
import com.services.tradedoubler.productservice.api.bo.ProductsFileStatusUpdateRequest;
import com.services.tradedoubler.productservice.exception.ServiceError;
import com.services.tradedoubler.productservice.model.FileStatus;
import com.services.tradedoubler.productservice.model.ProductsFileEntity;
import com.services.tradedoubler.productservice.repository.ProductsFileRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

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
        ProductsFileEntity entity = new ProductsFileEntity(file.getOriginalFilename());
        fileStorageService.addProductsFileToBucket(entity.getId(),file);
        log.info("product file {} uploaded successfully", file.getOriginalFilename());
        return productsFileRepository.save(entity);
    }

    public ProductsFileEntity updateProductsFileStatus(final String fileId, final ProductsFileStatusUpdateRequest productsFileStatusUpdateRequest) {
        log.info("Updating product file with id {} status", fileId);
        ProductsFileEntity entity = getProductsFileEntityById(fileId);
        entity.setStatus(productsFileStatusUpdateRequest.getStatus());
        entity.setComment(productsFileStatusUpdateRequest.getComment());
        return productsFileRepository.save(entity);
    }

    public List<ProductsFileDto> getProductsFilesByStatus(final FileStatus status){
        return productsFileRepository.findByStatus(status)
                .stream()
                .map(entity -> ProductsFileDto.builder()
                        .id(entity.getId())
                        .fileName(entity.getFileName())
                        .status(entity.getStatus())
                        .comment(entity.getComment())
                        .build())
                .collect(Collectors.toList());
    }

    public String getProductsFileContent(final String fileId) {
        log.info("Retrieve product file with id {} content", fileId);
        ProductsFileEntity entity = getProductsFileEntityById(fileId);
        try {
            final String fileName = entity.getId()+FileStorageService.SEPARATOR+entity.getFileName();
            return fileStorageService.loadProductsFileByName(fileName)
                    .getContentAsString(Charset.defaultCharset());
        } catch (IOException e) {
           throw ServiceError.INTERNAL_SERVER_ERROR.buildException(e.getMessage());
        }
    }

    public ProductsFileDto getProductFile(final String fileId){
        ProductsFileEntity entity = getProductsFileEntityById(fileId);
        return ProductsFileDto.builder()
                .id(entity.getId())
                .fileName(entity.getFileName())
                .status(entity.getStatus())
                .comment(entity.getComment())
                .build();
    }
    private  ProductsFileEntity getProductsFileEntityById(final String fileId){
       return productsFileRepository.findById(fileId).orElseThrow(()-> ServiceError.NOT_FOUND_PRODUCTS_FILE.buildException());
    }

}

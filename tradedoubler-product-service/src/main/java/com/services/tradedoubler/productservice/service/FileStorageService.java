package com.services.tradedoubler.productservice.service;

import com.services.tradedoubler.productservice.config.FileStorageProperties;
import com.services.tradedoubler.productservice.exception.ServiceError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileStorageService {

    public final static String SEPARATOR = "-";
    private final FileStorageProperties fileStorageProperties;
    private final Path root;

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
        this.root =  Paths.get(fileStorageProperties.getBucketName());
    }

    public void initProductsFileBucket(){
        log.info("Initiate Products files bucket ...");
        try {
            Files.createDirectories(root);
            log.info("Products files bucket is created successfully");
        } catch (IOException e) {
            log.error("Error while initiating Products files bucket Due to Error: "+e.getLocalizedMessage());
            throw ServiceError.INTERNAL_SERVER_ERROR.buildException(e.getLocalizedMessage());
        }
    }

    public void addProductsFileToBucket(String fileId, MultipartFile file) {
        try {
            log.info("upload products file {} to the bucket", file.getOriginalFilename());
            Files.copy(file.getInputStream(), this.root.resolve(fileId+SEPARATOR+file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                log.warn("upload duplicate products file {} to the bucket", file.getOriginalFilename());
               throw ServiceError.DUPLICATE_FILES_WITH_THE_SAME_NAME.buildException();
            }
            log.error("Error while uploading products file {} to the bucket Due to error: {}", file.getOriginalFilename(), e.getLocalizedMessage());
            throw ServiceError.INTERNAL_SERVER_ERROR.buildException(e.getLocalizedMessage());
        }
    }

    public Resource loadProductsFileByName(String filename) {
        try {
            log.info("load products file {} from the bucket",filename);
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("Error while loading products file {} from the bucket , not exist or non readable file",filename);
                throw ServiceError.ERROR_NOT_READABLE_FILE.buildException();
            }
        } catch (MalformedURLException e) {
            log.error("Error while loading products file {} from the bucket due to error {}",filename, e.getLocalizedMessage());
            throw ServiceError.INTERNAL_SERVER_ERROR.buildException(e.getLocalizedMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }
}

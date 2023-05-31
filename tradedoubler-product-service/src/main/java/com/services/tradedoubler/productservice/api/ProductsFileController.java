package com.services.tradedoubler.productservice.api;

import com.services.tradedoubler.productservice.model.ProductsFileEntity;
import com.services.tradedoubler.productservice.api.bo.ProductsFileStatusUpdateRequest;
import com.services.tradedoubler.productservice.service.ProductsFileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

import static com.services.tradedoubler.productservice.TradeDoublerProductServiceApplication.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1+"/products/files")
public class ProductsFileController {

    private final ProductsFileService productsFileService;

    public ProductsFileController(ProductsFileService productsFileService) {
        this.productsFileService = productsFileService;
    }

    @PostMapping("")
    public ResponseEntity<?> uploadProductsFile(@RequestParam("file") MultipartFile file) {
        ProductsFileEntity productsFileEntity = productsFileService.uploadProductFile(file);
       return ResponseEntity.created(URI.create(API_VERSION_1+"/products/files/"+ productsFileEntity.getId())).build();
    }

    @PutMapping(path = "/{fileId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProductsFileStatus(@NotBlank @PathVariable String fileId, @Valid @RequestBody ProductsFileStatusUpdateRequest productsFileStatusUpdateRequest) {
        productsFileService.updateProductsFileStatus(fileId, productsFileStatusUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{fileId}/content", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getProductsFileContent(@NotBlank @PathVariable String fileId) {
        String content = productsFileService.getProductsFileContent(fileId);
        return ResponseEntity.ok(content);
    }
}

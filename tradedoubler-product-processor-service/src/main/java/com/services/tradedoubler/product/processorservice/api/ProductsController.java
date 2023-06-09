package com.services.tradedoubler.product.processorservice.api;

import com.services.tradedoubler.product.processorservice.api.bo.ExportFileType;
import com.services.tradedoubler.product.processorservice.service.ProductsExportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.services.tradedoubler.product.processorservice.TradeDoublerProductProcessorServiceApplication.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1+"/products")
public class ProductsController {

    private final ProductsExportService productsExportService;

    public ProductsController(ProductsExportService productsExportService) {
        this.productsExportService = productsExportService;
    }

    @GetMapping(path = "/export")
    public ResponseEntity<?> exportProducts(@RequestParam("productsFileId") String productsFileId, @RequestParam("exportFileType") ExportFileType exportFileType){
        ByteArrayResource resource = productsExportService.exportProducts(productsFileId, exportFileType);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=result.%s",exportFileType.name().toLowerCase()))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}

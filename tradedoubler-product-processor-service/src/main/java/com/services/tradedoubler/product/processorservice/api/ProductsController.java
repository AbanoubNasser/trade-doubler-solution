package com.services.tradedoubler.product.processorservice.api;

import com.services.tradedoubler.product.processorservice.api.bo.ExportFileType;
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

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> exportProducts(@RequestParam("productsFileId") String productsFileId, @RequestParam("exportFileType") ExportFileType exportFileType){
      return null;
    }
}

package com.services.tradedoubler.product.processorservice.integration;

import com.services.tradedoubler.product.processorservice.integration.data.ProductsFile;
import com.services.tradedoubler.product.processorservice.integration.data.ProductsFileStatusUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "products-file-client", url = "${productFileService.url}")
public interface ProductsFileClient {

    @RequestMapping(method = RequestMethod.GET, value = "/files/{fileId}/content")
    String getProductsFileContent(@PathVariable("fileId") String fileId);

    @RequestMapping(method = RequestMethod.GET, value = "/files")
    List<ProductsFile> getProductsFilesByStatus(@RequestParam("status") String fileStatus);

    @RequestMapping(method = RequestMethod.PUT, value = "/files/{fileId}")
    void updateProcessedProductsFileStatus(@PathVariable("fileId") String fileId, @RequestBody ProductsFileStatusUpdateRequest productsFileStatusUpdateRequest);
}

package com.services.tradedoubler.product.processorservice.scheduler;

import com.services.tradedoubler.product.processorservice.bo.Product;
import com.services.tradedoubler.product.processorservice.exception.ServiceException;
import com.services.tradedoubler.product.processorservice.integration.data.FileStatus;
import com.services.tradedoubler.product.processorservice.integration.data.ProductsFile;
import com.services.tradedoubler.product.processorservice.service.ProductService;
import com.services.tradedoubler.product.processorservice.service.ProductsFileService;
import com.services.tradedoubler.product.processorservice.service.ProductsProcessorService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class ProductsProcessorScheduler {

    private final ProductsFileService productsFileService;
    private final ProductsProcessorService productsProcessorService;
    private final ProductService productService;

    public ProductsProcessorScheduler(ProductsFileService productsFileService, ProductsProcessorService productsProcessorService, ProductService productService) {
        this.productsFileService = productsFileService;
        this.productsProcessorService = productsProcessorService;
        this.productService = productService;
    }

    @Scheduled(cron = "${products-scheduler-properties.cron-expression}")
    @SchedulerLock(name = "ProductsFileProcessing_scheduledTask", lockAtLeastFor = "${products-scheduler-properties.lock-at-least}", lockAtMostFor = "${products-scheduler-properties.lock-at-most}")
    public void processProductsFiles() {
        log.info("ProductsFileProcessing_scheduledTask had been started ...");
        List<ProductsFile> retrievedProductsFiles = productsFileService.getProductsFilesByStatus(FileStatus.UPLOADED);
        log.info("Number of product files to be processed is {}", retrievedProductsFiles.size());
        retrievedProductsFiles.parallelStream()
                .forEach(entry -> {
                    try{
                        log.info("Request content of products file {} ",entry.getFileName());
                        String productsFileContent = productsFileService.getProductsFileContent(entry.getId());
                        log.info("Start process content of products file {} ",entry.getFileName());
                        Set<Product> products = productsProcessorService.processProducts(productsFileContent, entry.getId());
                        log.info("Start persist content of products file {} ",entry.getFileName());
                        productService.createProducts(products);
                        log.info("Update products file {} status ",entry.getFileName());
                        productsFileService.updateProductsFileStatus(entry.getId(), FileStatus.SUCCEEDED, "File is successfully processed");
                    }catch (ServiceException exception){
                        log.error("Error while processing products file {} due to {} ", entry.getFileName(), exception.getMessage());
                        productsFileService.updateProductsFileStatus(entry.getId(), FileStatus.FAILED_TO_PROCESS, exception.getMessage());
                    }
                });
    }
}

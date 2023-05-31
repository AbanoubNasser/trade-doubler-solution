package com.services.tradedoubler.product.processorservice.scheduler;

import com.services.tradedoubler.product.processorservice.integration.data.FileStatus;
import com.services.tradedoubler.product.processorservice.service.ProductsFileService;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductsProcessorScheduler {

    private final ProductsFileService productsFileService;

    public ProductsProcessorScheduler(ProductsFileService productsFileService) {
        this.productsFileService = productsFileService;
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    @SchedulerLock(name = "TaskScheduler_scheduledTask", lockAtLeastFor = "PT30S", lockAtMostFor = "PT1M")
    public void processProductsFiles() {
        productsFileService.getProductsFilesByStatus(FileStatus.UPLOADED)
                .forEach(entry -> {
                    productsFileService.updateProductsFileStatus(entry.getId(), FileStatus.IN_PROGRESS, "File in progress");
                });
    }
}

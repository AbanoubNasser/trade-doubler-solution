package com.services.tradedoubler.productservice;

import com.services.tradedoubler.productservice.service.FileStorageService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradeDoublerProductServiceApplication implements CommandLineRunner {

    @Resource
    FileStorageService fileStorageService;
    public static final String API_VERSION_1 = "/api/v1";

    public static void main(String[] args) {
        SpringApplication.run(TradeDoublerProductServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fileStorageService.initProductsFileBucket();
    }
}

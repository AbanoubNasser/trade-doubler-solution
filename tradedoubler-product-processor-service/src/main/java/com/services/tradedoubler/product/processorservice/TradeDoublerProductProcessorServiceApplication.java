package com.services.tradedoubler.product.processorservice;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class TradeDoublerProductProcessorServiceApplication {

    public static final String API_VERSION_1 = "/api/v1";

    public static void main(String[] args) {
        SpringApplication.run(TradeDoublerProductProcessorServiceApplication.class, args);
    }

}

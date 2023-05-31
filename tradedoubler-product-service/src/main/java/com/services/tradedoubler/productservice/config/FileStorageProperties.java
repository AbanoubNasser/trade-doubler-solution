package com.services.tradedoubler.productservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file-storage")
@Getter
@Setter
public class FileStorageProperties {

    private String bucketName;
}

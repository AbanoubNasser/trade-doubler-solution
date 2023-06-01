package com.services.tradedoubler.product.processorservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "products-properties")
@Getter
@Setter
public class ProductProperties {

    private String schemaFileName;
}

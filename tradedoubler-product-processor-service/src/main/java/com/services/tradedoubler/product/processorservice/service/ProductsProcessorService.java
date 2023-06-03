package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.bo.Product;
import com.services.tradedoubler.product.processorservice.bo.Result;
import com.services.tradedoubler.product.processorservice.config.ProductProperties;
import com.services.tradedoubler.product.processorservice.utils.XmlUtility;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProductsProcessorService {

    private final XmlUtility xmlUtility;
    private final ProductProperties productProperties;

    public ProductsProcessorService(XmlUtility xmlUtility, ProductProperties productProperties) {
        this.xmlUtility = xmlUtility;
        this.productProperties = productProperties;
    }

    public Set<Product> processProducts(final String productsXmlContent){
        xmlUtility.validate(productsXmlContent, productProperties.getSchemaFileName());
        Result productsResult = xmlUtility.parseXml(productsXmlContent, Result.class);
        return productsResult.getProducts();
    }
}

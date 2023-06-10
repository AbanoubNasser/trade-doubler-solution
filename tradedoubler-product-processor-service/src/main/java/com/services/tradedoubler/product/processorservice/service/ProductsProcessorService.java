package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.bo.Product;
import com.services.tradedoubler.product.processorservice.bo.Result;
import com.services.tradedoubler.product.processorservice.config.ProductProperties;
import com.services.tradedoubler.product.processorservice.utils.XmlUtility;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductsProcessorService {

    private final XmlUtility xmlUtility;
    private final ProductProperties productProperties;

    public ProductsProcessorService(XmlUtility xmlUtility, ProductProperties productProperties) {
        this.xmlUtility = xmlUtility;
        this.productProperties = productProperties;
    }

    public Set<Product> processProducts(final String productsXmlContent, String productsFileId){
        xmlUtility.validate(productsXmlContent);
        Result productsResult = xmlUtility.parseXml(productsXmlContent, Result.class);
        return productsResult.getProducts().parallelStream().map(product ->{
            product.setProductFileId(productsFileId);
            return product;
        }).collect(Collectors.toSet());
    }
}

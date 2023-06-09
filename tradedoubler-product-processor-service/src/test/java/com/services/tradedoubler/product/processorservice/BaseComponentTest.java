package com.services.tradedoubler.product.processorservice;
import com.services.tradedoubler.product.processorservice.bo.Product;
import com.services.tradedoubler.product.processorservice.bo.Result;
import com.services.tradedoubler.product.processorservice.utils.XmlUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

public class BaseComponentTest {

    private XmlUtility xmlUtility = new XmlUtility();

    protected Set<Product> buildMappedProducts() throws IOException {
        Result result = xmlUtility.parseXml(getResourceFileAsString("Products.xml"), Result.class);
        return result.getProducts();
    }

    protected Product getProduct() throws IOException {
       return buildMappedProducts().iterator().next();
    }

    protected String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}

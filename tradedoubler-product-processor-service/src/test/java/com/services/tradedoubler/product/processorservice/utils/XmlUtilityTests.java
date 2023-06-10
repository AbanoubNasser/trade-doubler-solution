package com.services.tradedoubler.product.processorservice.utils;

import com.services.tradedoubler.product.processorservice.SpringBootComponentTest;
import com.services.tradedoubler.product.processorservice.bo.Result;
import com.services.tradedoubler.product.processorservice.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class XmlUtilityTests extends SpringBootComponentTest {

    @Autowired
    private XmlUtility xmlUtility;

    @Test
    public void testValidationWithInvalidProductData() {
        assertThrows(ServiceException.class, ()-> xmlUtility.validate(getResourceFileAsString("Invalid_Products.xml")));
    }

    @Test
    public void testValidationSuccessfully() {
        assertDoesNotThrow(() -> xmlUtility.validate(getResourceFileAsString("Products.xml")));
    }

    @Test
    public void testParsingWithInvalidProductData() {
        assertThrows(ServiceException.class, ()-> xmlUtility.parseXml("<Student><name>test</name></Student>", Result.class));
    }

    @Test
    public void testParsingSuccessfully() throws IOException {
        Result result = xmlUtility.parseXml(getResourceFileAsString("Products.xml"), Result.class);
        assertNotNull(result);
        assertEquals(3, result.getProducts().size());
    }

}

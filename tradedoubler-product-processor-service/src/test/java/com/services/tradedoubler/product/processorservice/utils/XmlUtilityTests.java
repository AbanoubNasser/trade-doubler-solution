package com.services.tradedoubler.product.processorservice.utils;

import com.services.tradedoubler.product.processorservice.bo.Result;
import com.services.tradedoubler.product.processorservice.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.validation.Schema;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class XmlUtilityTests {

    private XmlUtility xmlUtility;

    @BeforeEach
    void init(){
        xmlUtility = new XmlUtility();
    }

    @Test
    public void testNotFoundSchema() {
        assertThrows(ServiceException.class, ()-> xmlUtility.validate(getResourceFileAsString("Products.xml"), "Not_Valid.xsd"));
    }

    @Test
    public void testValidationWithInvalidProductData() {
        assertThrows(ServiceException.class, ()-> xmlUtility.validate(getResourceFileAsString("Invalid_Products.xml"), "Products_Def.xsd"));
    }

    @Test
    public void testValidationSuccessfully() {
        assertDoesNotThrow(() -> xmlUtility.validate(getResourceFileAsString("Products.xml"), "Products_Def.xsd"));
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

    private String getResourceFileAsString(String fileName) throws IOException {
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

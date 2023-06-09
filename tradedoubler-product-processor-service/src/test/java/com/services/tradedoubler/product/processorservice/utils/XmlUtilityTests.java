package com.services.tradedoubler.product.processorservice.utils;

import com.services.tradedoubler.product.processorservice.BaseComponentTest;
import com.services.tradedoubler.product.processorservice.bo.Result;
import com.services.tradedoubler.product.processorservice.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class XmlUtilityTests extends BaseComponentTest {

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

}

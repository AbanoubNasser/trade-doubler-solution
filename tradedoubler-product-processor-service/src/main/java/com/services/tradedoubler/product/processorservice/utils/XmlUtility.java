package com.services.tradedoubler.product.processorservice.utils;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.services.tradedoubler.product.processorservice.exception.ServiceError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

@Service
@Slf4j
public class XmlUtility {
    private final XmlMapper xmlMapper;
    private final ResourceLoader resourceLoader;

    public XmlUtility(ResourceLoader resourceLoader) {
        this.xmlMapper = new XmlMapper();
        this.resourceLoader = resourceLoader;
    }

    public void validate(String xmlContent){
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schemaFactory.setResourceResolver(new ClasspathResourceResolver());
        try {
            StreamSource xsdSource = new StreamSource(getSchemaFile());
            Schema schema = schemaFactory.newSchema(xsdSource);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlContent)));
        } catch (SAXException | IOException e) {
           throw ServiceError.INVALID_XML_CONTENT.buildException(e.getLocalizedMessage());
        }
    }

    public <T> T parseXml(String xmlContent, Class<?> target){
        T obj = null;
        try {
            obj = (T) xmlMapper.readValue(xmlContent, target);

        } catch (IOException e) {
            throw ServiceError.INVALID_XML_MAPPING.buildException();
        }
        return obj;
    }
    private InputStream getSchemaFile() {
        try{
            if(resourceLoader!=null){
                return resourceLoader.getResource("classpath:/Products_Def.xsd").getInputStream();
            }else{
                return  new ClassPathResource("Products_Def.xsd").getInputStream();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw ServiceError.ERROR_WHILE_LOAD_SCHEMA_FILE.buildException();
        }

    }
}

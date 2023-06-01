package com.services.tradedoubler.product.processorservice.bo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "productImage")
public class ProductImage {

    @JacksonXmlText
    protected String value;

    @JacksonXmlProperty(localName = "height",isAttribute = true)
    protected Integer height;

    @JacksonXmlProperty(localName = "width",isAttribute = true)
    protected Integer width;
}

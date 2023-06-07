package com.services.tradedoubler.product.processorservice.api.bo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class Result {

    @JacksonXmlElementWrapper(localName = "Products")
    @JacksonXmlProperty(localName = "Product")
    private Set<ProductDto> products;
}

package com.services.tradedoubler.product.processorservice.bo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "category")
public class Category {

    @JacksonXmlProperty(localName = "name",isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "id",isAttribute = true)
    private Integer categoryId;

    @JacksonXmlProperty(localName = "tdCategoryName",isAttribute = true)
    private String tdCategoryName;
}

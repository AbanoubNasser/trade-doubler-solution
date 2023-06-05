package com.services.tradedoubler.product.processorservice.bo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "product")
public class Product implements Serializable {

    private String productFileId; 
    
    @JacksonXmlProperty(localName = "groupingId",isAttribute = true)
    private String groupingId;

    @JacksonXmlProperty(localName = "language",isAttribute = true)
    private String language;

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "description")
    private String description;

    @JacksonXmlProperty(localName = "productImage")
    private ProductImage productImage;

    @JacksonXmlElementWrapper(localName = "categories")
    @JacksonXmlProperty(localName = "category")
    private Set<Category> categories;

    @JacksonXmlElementWrapper(localName = "fields")
    @JacksonXmlProperty(localName = "field")
    private Set<Field> fields;

    @JacksonXmlProperty(localName = "weight")
    private String weight;

    @JacksonXmlProperty(localName = "size")
    private String size;

    @JacksonXmlProperty(localName = "model")
    private String model;

    @JacksonXmlProperty(localName = "brand")
    private String brand;

    @JacksonXmlProperty(localName = "manufacturer")
    private String manufacturer;

    @JacksonXmlProperty(localName = "techSpecs")
    private String techSpecs;

    @JacksonXmlProperty(localName = "shortDescription")
    private String shortDescription;

    @JacksonXmlProperty(localName = "promoText")
    private String promoText;

    @JacksonXmlProperty(localName = "ean")
    private String ean;

    @JacksonXmlProperty(localName = "upc")
    private String upc;

    @JacksonXmlProperty(localName = "isbn")
    private String isbn;

    @JacksonXmlProperty(localName = "mpn")
    private String mpn;

    @JacksonXmlProperty(localName = "sku")
    private String sku;

    @JacksonXmlElementWrapper(localName = "offers")
    @JacksonXmlProperty(localName = "offer")
    private Set<Offer> offers;

    public void setProductFileId(String productFileId){
        this.productFileId = productFileId;
    }
}

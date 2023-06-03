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

    @JacksonXmlProperty(localName = "groupingId",isAttribute = true)
    protected String groupingId;

    @JacksonXmlProperty(localName = "language",isAttribute = true)
    protected String language;

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "description")
    protected String description;

    @JacksonXmlProperty(localName = "productImage")
    protected ProductImage productImage;

    @JacksonXmlElementWrapper(localName = "categories")
    @JacksonXmlProperty(localName = "category")
    protected Set<Category> categories;

    @JacksonXmlElementWrapper(localName = "fields")
    @JacksonXmlProperty(localName = "field")
    protected Set<Field> fields;

    @JacksonXmlProperty(localName = "weight")
    protected String weight;

    @JacksonXmlProperty(localName = "size")
    protected String size;

    @JacksonXmlProperty(localName = "model")
    protected String model;

    @JacksonXmlProperty(localName = "brand")
    protected String brand;

    @JacksonXmlProperty(localName = "manufacturer")
    protected String manufacturer;

    @JacksonXmlProperty(localName = "techSpecs")
    protected String techSpecs;

    @JacksonXmlProperty(localName = "shortDescription")
    protected String shortDescription;

    @JacksonXmlProperty(localName = "promoText")
    protected String promoText;

    @JacksonXmlProperty(localName = "ean")
    protected String ean;

    @JacksonXmlProperty(localName = "upc")
    protected String upc;

    @JacksonXmlProperty(localName = "isbn")
    protected String isbn;

    @JacksonXmlProperty(localName = "mpn")
    protected String mpn;

    @JacksonXmlProperty(localName = "sku")
    protected String sku;

    @JacksonXmlElementWrapper(localName = "offers")
    @JacksonXmlProperty(localName = "offer")
    protected Set<Offer> offers;
}

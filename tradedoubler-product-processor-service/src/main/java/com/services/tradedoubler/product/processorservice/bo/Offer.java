package com.services.tradedoubler.product.processorservice.bo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "offer")
public class Offer {

    @JacksonXmlProperty(localName = "feedId")
    protected Long feedId;

    @JacksonXmlProperty(localName = "productUrl")
    protected String productUrl;

    @JacksonXmlProperty(localName = "programName")
    protected String programName;

    @JacksonXmlProperty(localName = "programLogo")
    protected String programLogo;

    @JacksonXmlProperty(localName = "priceHistory")
    protected PriceHistory priceHistory;

    @JacksonXmlProperty(localName = "warranty")
    protected String warranty;


    @JacksonXmlProperty(localName = "inStock")
    protected Integer inStock;

    @JacksonXmlProperty(localName = "availability")
    protected String availability;

   @JacksonXmlProperty(localName = "deliveryTime")
    protected String deliveryTime;

    @JacksonXmlProperty(localName = "condition")
    protected String condition;

    @JacksonXmlProperty(localName = "shippingCost")
    protected String shippingCost;

    @JacksonXmlProperty(localName = "id",isAttribute = true)
    protected String id;

    @JacksonXmlProperty(localName = "sourceProductId",isAttribute = true)
    protected String sourceProductId;


    @JacksonXmlProperty(localName = "modifiedDate",isAttribute = true)
    protected Long modifiedDate;

    @JacksonXmlProperty(localName = "dateFormat",isAttribute = true)
    protected String dateFormat;

}

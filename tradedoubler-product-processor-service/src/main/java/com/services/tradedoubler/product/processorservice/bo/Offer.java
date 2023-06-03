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
    private Long feedId;

    @JacksonXmlProperty(localName = "productUrl")
    private String productUrl;

    @JacksonXmlProperty(localName = "programName")
    private String programName;

    @JacksonXmlProperty(localName = "programLogo")
    private String programLogo;

    @JacksonXmlProperty(localName = "priceHistory")
    private PriceHistory priceHistory;

    @JacksonXmlProperty(localName = "warranty")
    private String warranty;


    @JacksonXmlProperty(localName = "inStock")
    private Integer inStock;

    @JacksonXmlProperty(localName = "availability")
    private String availability;

   @JacksonXmlProperty(localName = "deliveryTime")
    private String deliveryTime;

    @JacksonXmlProperty(localName = "condition")
    private String condition;

    @JacksonXmlProperty(localName = "shippingCost")
    private String shippingCost;

    @JacksonXmlProperty(localName = "id",isAttribute = true)
    private String offerId;

    @JacksonXmlProperty(localName = "sourceProductId",isAttribute = true)
    private String sourceProductId;


    @JacksonXmlProperty(localName = "modifiedDate",isAttribute = true)
    private Long modifiedDate;

    @JacksonXmlProperty(localName = "dateFormat",isAttribute = true)
    private String dateFormat;

}

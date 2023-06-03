package com.services.tradedoubler.product.processorservice.bo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "price")
public class Price {

    @JacksonXmlText
    private BigDecimal value;

    @JacksonXmlProperty(localName = "currency",isAttribute = true)
    private String currency;


    @JacksonXmlProperty(localName = "date",isAttribute = true)
    private Long date;

    @JacksonXmlProperty(localName = "dateFormat",isAttribute = true)
    private String dateFormat;
}

package com.services.tradedoubler.product.processorservice.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.services.tradedoubler.product.processorservice.base.entity.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "price")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PriceEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal value;

    private String currency;

    private Long date;

    private String dateFormat;

    @OneToOne
    @MapsId
    @JoinColumn(name = "offer_id")
    private OfferEntity offer;
}

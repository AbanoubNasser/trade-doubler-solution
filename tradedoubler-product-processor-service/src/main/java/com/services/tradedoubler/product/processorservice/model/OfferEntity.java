package com.services.tradedoubler.product.processorservice.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.services.tradedoubler.product.processorservice.base.entity.Auditable;
import com.services.tradedoubler.product.processorservice.bo.PriceHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(name = "offer")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OfferEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long feedId;

    private String productUrl;

    private String programName;

    private String programLogo;

    @OneToOne(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private PriceEntity price;

    private String warranty;

    private Integer inStock;

    private String availability;

    private String deliveryTime;

    private String condition;

    private String shippingCost;

    private String offerId;

    private String sourceProductId;

    private Long modifiedDate;

    private String dateFormat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductEntity product;

}

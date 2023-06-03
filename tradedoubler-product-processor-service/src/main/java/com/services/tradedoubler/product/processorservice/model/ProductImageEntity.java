package com.services.tradedoubler.product.processorservice.model;

import com.services.tradedoubler.product.processorservice.base.entity.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "product_image")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String value;

    private Integer height;

    private Integer width;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}

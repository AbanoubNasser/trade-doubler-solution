package com.services.tradedoubler.product.processorservice.model;

import com.services.tradedoubler.product.processorservice.base.entity.Auditable;
import com.services.tradedoubler.product.processorservice.bo.Category;
import com.services.tradedoubler.product.processorservice.bo.Field;
import com.services.tradedoubler.product.processorservice.bo.Offer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String productFileId;

    private String groupingId;

    private String language;

    private String name;

    private String description;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<CategoryEntity> categories;

    private String weight;

    private String size;

    private String model;

    private String brand;

    private String manufacturer;

    private String techSpecs;

    private String shortDescription;

    private String promoText;

    private String ean;

    private String upc;

    private String isbn;

    private String mpn;

    private String sku;
}

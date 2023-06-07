package com.services.tradedoubler.product.processorservice.api.bo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.services.tradedoubler.product.processorservice.model.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@JacksonXmlRootElement(localName = "Product")
public class ProductDto {

    private UUID id;

    private String productFileId;

    private String groupingId;

    private String language;

    private String name;

    private String description;

    private Set<CategoryDto> categories;

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

    private ProductImageDto productImage;

    private Set<OfferDto> offers;

    private Set<FieldDto> fields;

    public static ProductDto mapToDto(ProductEntity productEntity,
                                      ProductImageEntity productImageEntity,
                                      List<FieldEntity> fieldEntitySet,
                                      Map<OfferEntity, PriceEntity> offerPricesMap) {
        return ProductDto.builder()
                .id(productEntity.getId())
                .productFileId(productEntity.getProductFileId())
                .groupingId(productEntity.getGroupingId())
                .language(productEntity.getLanguage())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .categories(CategoryDto.mapToDto(productEntity.getCategories()))
                .weight(productEntity.getWeight())
                .size(productEntity.getSize())
                .model(productEntity.getModel())
                .brand(productEntity.getBrand())
                .manufacturer(productEntity.getManufacturer())
                .techSpecs(productEntity.getTechSpecs())
                .shortDescription(productEntity.getShortDescription())
                .promoText(productEntity.getPromoText())
                .ean(productEntity.getEan())
                .upc(productEntity.getUpc())
                .isbn(productEntity.getIsbn())
                .mpn(productEntity.getMpn())
                .sku(productEntity.getSku())
                .productImage(ProductImageDto.mapToDto(productImageEntity))
                .fields(FieldDto.mapToDto(fieldEntitySet))
                .offers(OfferDto.mapToDto(offerPricesMap))
                .build();
    }

    public String getProductImage(){
        return this.productImage.getValue();
    }

    public String getOffers() {
        return this.offers.stream().map(offer -> offer.getOfferId()).collect( Collectors.joining( "&" ) );
    }

    public String getFields(){
        return this.fields.stream().map(field -> String.format("%s:%S",field.getName(),field.getValue())).collect( Collectors.joining( "&" ) );
    }
}

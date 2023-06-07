package com.services.tradedoubler.product.processorservice.api.bo;

import com.services.tradedoubler.product.processorservice.model.OfferEntity;
import com.services.tradedoubler.product.processorservice.model.PriceEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
public class OfferDto {

    private UUID id;

    private Long feedId;

    private String productUrl;

    private String programName;

    private String programLogo;

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

    private PriceDto price;

    public static Set<OfferDto> mapToDto(Map<OfferEntity, PriceEntity> offerPricesMap){
        return offerPricesMap.entrySet().stream().map(entry -> mapToDto(entry.getKey(), entry.getValue())).collect(Collectors.toSet());
    }

    private static OfferDto mapToDto(OfferEntity offerEntity, PriceEntity priceEntity){
        return OfferDto.builder()
                .id(offerEntity.getId())
                .feedId(offerEntity.getFeedId())
                . productUrl(offerEntity.getProductUrl())
                .programName(offerEntity.getProgramName())
                .programLogo(offerEntity.getProgramLogo())
                .warranty(offerEntity.getWarranty())
                .inStock(offerEntity.getInStock())
                .availability(offerEntity.getAvailability())
                .deliveryTime(offerEntity.getDeliveryTime())
                .condition(offerEntity.getCondition())
                .shippingCost(offerEntity.getShippingCost())
                .offerId(offerEntity.getOfferId())
                .sourceProductId(offerEntity.getSourceProductId())
                .modifiedDate(offerEntity.getModifiedDate())
                .dateFormat(offerEntity.getDateFormat())
                .price(PriceDto.mapToDto(priceEntity))
                .build();

    }
}

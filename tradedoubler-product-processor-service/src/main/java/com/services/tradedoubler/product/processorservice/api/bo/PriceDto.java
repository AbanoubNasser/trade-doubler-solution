package com.services.tradedoubler.product.processorservice.api.bo;

import com.services.tradedoubler.product.processorservice.model.FieldEntity;
import com.services.tradedoubler.product.processorservice.model.PriceEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class PriceDto {

    private UUID id;

    private BigDecimal value;

    private String currency;

    private Long date;

    private String dateFormat;

    public static PriceDto mapToDto(PriceEntity priceEntity){
        return PriceDto.builder()
                .id(priceEntity.getId())
                .value(priceEntity.getValue())
                .currency(priceEntity.getCurrency())
                .date(priceEntity.getDate())
                .dateFormat(priceEntity.getDateFormat())
                .build();

    }
}

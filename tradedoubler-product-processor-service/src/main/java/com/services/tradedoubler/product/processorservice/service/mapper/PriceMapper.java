package com.services.tradedoubler.product.processorservice.service.mapper;


import com.services.tradedoubler.product.processorservice.bo.Price;
import com.services.tradedoubler.product.processorservice.model.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceEntity map(Price price);

}

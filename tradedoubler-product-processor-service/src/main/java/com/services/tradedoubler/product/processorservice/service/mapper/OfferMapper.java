package com.services.tradedoubler.product.processorservice.service.mapper;

import com.services.tradedoubler.product.processorservice.bo.Offer;
import com.services.tradedoubler.product.processorservice.model.OfferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface OfferMapper {


    OfferEntity map(Offer offer);
    Set<OfferEntity> mapToList(Set<Offer> offers);
}

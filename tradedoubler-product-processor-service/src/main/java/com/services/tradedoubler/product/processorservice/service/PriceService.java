package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.bo.Price;
import com.services.tradedoubler.product.processorservice.exception.ServiceError;
import com.services.tradedoubler.product.processorservice.model.OfferEntity;
import com.services.tradedoubler.product.processorservice.model.PriceEntity;
import com.services.tradedoubler.product.processorservice.repository.PriceRepository;
import com.services.tradedoubler.product.processorservice.service.mapper.PriceMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    private final PriceMapper priceMapper;

    public PriceService(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    public void createOfferPrice(final OfferEntity offerEntity, final Price price){
        PriceEntity priceEntity = priceMapper.map(price);
        priceEntity.setOffer(offerEntity);
        priceRepository.save(priceEntity);
    }

    public PriceEntity getOfferPrice(final UUID offerId){
        return priceRepository.findByOfferId(offerId).orElseThrow(()-> ServiceError.NOT_FOUND_OFFER_PRICE.buildException());
    }
}

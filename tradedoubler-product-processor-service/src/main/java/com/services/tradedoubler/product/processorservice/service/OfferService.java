package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.bo.Offer;
import com.services.tradedoubler.product.processorservice.model.OfferEntity;
import com.services.tradedoubler.product.processorservice.model.PriceEntity;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import com.services.tradedoubler.product.processorservice.repository.OfferRepository;
import com.services.tradedoubler.product.processorservice.service.mapper.OfferMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;

    private final PriceService priceService;

    public OfferService(OfferRepository offerRepository, OfferMapper offerMapper, PriceService priceService) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.priceService = priceService;
    }


    public void createProductOffers(ProductEntity productEntity, Set<Offer> offers){
        offers.stream().forEach(offer ->{
            OfferEntity offerEntity = offerMapper.map(offer);
            offerEntity.setProduct(productEntity);
            OfferEntity persistedOffer = offerRepository.save(offerEntity);
            priceService.createOfferPrice(persistedOffer, offer.getPriceHistory().getPrice());
        });
    }

    public Map<OfferEntity, PriceEntity> getProductOffers(final UUID productId){
        List<OfferEntity> offerEntities = offerRepository.findByProductId(productId);
        final Map<OfferEntity, PriceEntity> offerPricesMap = new HashMap<>();
        offerEntities.parallelStream().forEach(entry ->{
            offerPricesMap.put(entry,priceService.getOfferPrice(entry.getId()));
        });
        return offerPricesMap;
    }
}

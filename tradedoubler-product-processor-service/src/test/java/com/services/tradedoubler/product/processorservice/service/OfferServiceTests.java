package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.BaseComponentTest;
import com.services.tradedoubler.product.processorservice.bo.Offer;
import com.services.tradedoubler.product.processorservice.model.OfferEntity;
import com.services.tradedoubler.product.processorservice.model.PriceEntity;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import com.services.tradedoubler.product.processorservice.repository.OfferRepository;
import com.services.tradedoubler.product.processorservice.service.mapper.OfferMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTests extends BaseComponentTest {

    @Mock
    private OfferRepository offerRepository;
    @Mock
    private OfferMapper offerMapper;

    @Mock
    private PriceService priceService;

    @InjectMocks
    private OfferService offerService;

    @Test
    void testCreateProductsOffersSuccessfully() {
        when(offerMapper.map(any())).thenReturn(OfferEntity.builder().offerId("OfferId").condition("condition").availability("availability").build());
        when(offerRepository.save(any())).thenReturn(OfferEntity.builder().offerId("OfferId").condition("condition").availability("availability").build());
        doNothing().when(priceService).createOfferPrice(any(), any());
        assertDoesNotThrow(()-> offerService.createProductOffers(ProductEntity.builder().id(UUID.randomUUID()).name("productName").build(), buildOffers()));
    }

    @Test
    void testGetProductsOffersWithEmptyList() {
        when(offerRepository.findByProductId(any())).thenReturn(List.of());
        assertDoesNotThrow(() -> offerService.getProductOffers(UUID.randomUUID()));
    }

    @Test
    void testGetProductsOfferSuccessfully() {
        when(offerRepository.findByProductId(any())).thenReturn(List.of(OfferEntity.builder().offerId("offerId").id(UUID.randomUUID()).build()));
        when(priceService.getOfferPrice(any())).thenReturn(PriceEntity.builder().value(BigDecimal.valueOf(50)).currency("SEK").build());
        Map<OfferEntity, PriceEntity> resultMap = offerService.getProductOffers(UUID.randomUUID());

        verify(priceService, times(1)).getOfferPrice(any());
        assertEquals(1, resultMap.size());
    }

    private Set<Offer> buildOffers() throws IOException {
        return getProduct().getOffers();
    }

}

package com.services.tradedoubler.product.processorservice.service;

import com.services.tradedoubler.product.processorservice.BaseComponentTest;
import com.services.tradedoubler.product.processorservice.bo.Price;
import com.services.tradedoubler.product.processorservice.exception.ServiceException;
import com.services.tradedoubler.product.processorservice.model.OfferEntity;
import com.services.tradedoubler.product.processorservice.model.PriceEntity;
import com.services.tradedoubler.product.processorservice.repository.PriceRepository;
import com.services.tradedoubler.product.processorservice.service.mapper.PriceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTests extends BaseComponentTest {

    @Mock
    private  PriceRepository priceRepository;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PriceService priceService;


    @Test
    void testCreateOfferPriceSuccessfully() {
        when(priceMapper.map(any())).thenReturn(PriceEntity.builder().currency("SEK").value(BigDecimal.valueOf(50)).build());
        when(priceRepository.save(any())).thenReturn(PriceEntity.builder().id(UUID.randomUUID()).currency("SEK").value(BigDecimal.valueOf(50)).build());
        assertDoesNotThrow(()-> priceService.createOfferPrice(buildOfferEntity(), buildPrice()));
    }

    @Test
    void testGetOfferPriceWithEmpty() {
        when(priceRepository.findByOfferId(any())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, ()-> priceService.getOfferPrice(UUID.randomUUID()));
    }

    @Test
    void testGetOfferPriceSuccessfully() {
        when(priceRepository.findByOfferId(any())).thenReturn(Optional.of(PriceEntity.builder().id(UUID.randomUUID()).value(BigDecimal.valueOf(50)).build()));
        assertDoesNotThrow(()-> priceService.getOfferPrice(UUID.randomUUID()));
    }

    private OfferEntity buildOfferEntity(){
        return OfferEntity.builder().offerId("offerIf").id(UUID.randomUUID()).availability("Availability").build();
    }

    private Price buildPrice() throws IOException {
        return getProduct().getOffers().iterator().next().getPriceHistory().getPrice();
    }
}

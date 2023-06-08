package com.services.tradedoubler.product.processorservice.repository;

import com.services.tradedoubler.product.processorservice.SpringBootComponentTest;
import com.services.tradedoubler.product.processorservice.model.OfferEntity;
import com.services.tradedoubler.product.processorservice.model.PriceEntity;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PriceRepositoryIntegrationTests extends SpringBootComponentTest {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private OfferRepository offerRepository;


    @AfterEach
    void tearDown() {
        priceRepository.deleteAll();
        offerRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void testSaveAndRetrieveProductOfferPrice() {
        ProductEntity product = new ProductEntity();
        product.setName("ProductTest");
        ProductEntity productEntityFromRepo = productRepository.save(product);

        OfferEntity offerEntity = new OfferEntity();
        offerEntity.setFeedId(12234L);
        offerEntity.setOfferId("offerID");
        offerEntity.setProduct(productEntityFromRepo);

        OfferEntity offerEntityFromRepo = offerRepository.save(offerEntity);

        PriceEntity priceEntity =new PriceEntity();
        priceEntity.setOffer(offerEntityFromRepo);
        priceEntity.setValue(BigDecimal.valueOf(50,2));
        priceEntity.setCurrency("SEK");
        priceEntity.setDate(Long.valueOf(123456));

        PriceEntity priceEntityFromRepo = priceRepository.save(priceEntity);

        Optional<PriceEntity> priceEntityFromRepoOpt = priceRepository.findById(priceEntityFromRepo.getId());
        assertTrue(priceEntityFromRepoOpt.isPresent());
        assertEquals("SEK", priceEntityFromRepoOpt.get().getCurrency());
        assertEquals(BigDecimal.valueOf(50,2), priceEntityFromRepoOpt.get().getValue());
        assertEquals(Long.valueOf(123456), priceEntityFromRepoOpt.get().getDate());
    }
}

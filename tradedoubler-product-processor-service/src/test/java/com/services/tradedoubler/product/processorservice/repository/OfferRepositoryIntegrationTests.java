package com.services.tradedoubler.product.processorservice.repository;

import com.services.tradedoubler.product.processorservice.SpringBootComponentTest;
import com.services.tradedoubler.product.processorservice.model.OfferEntity;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class OfferRepositoryIntegrationTests extends SpringBootComponentTest {

    @Autowired
    private OfferRepository offerRepository;

    @AfterEach
    void tearDown() {
        offerRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void testSaveAndRetrieveProductOffer() {
        ProductEntity product = new ProductEntity();
        product.setName("ProductTest");
        ProductEntity productEntityFromRepo = productRepository.save(product);

        OfferEntity offerEntity = new OfferEntity();
        offerEntity.setFeedId(12234L);
        offerEntity.setOfferId("offerID");
        offerEntity.setProduct(productEntityFromRepo);

        OfferEntity offerEntityFromRepo = offerRepository.save(offerEntity);

        Optional<OfferEntity> OfferEntityFromRepoOpt = offerRepository.findById(offerEntityFromRepo.getId());
        assertTrue(OfferEntityFromRepoOpt.isPresent());
        assertEquals(Long.valueOf(12234), OfferEntityFromRepoOpt.get().getFeedId());
        assertEquals("offerID", OfferEntityFromRepoOpt.get().getOfferId());
    }

    @Test
    void testRetrieveProductFieldsByProductId() {
        ProductEntity product = new ProductEntity();
        product.setName("ProductTest");
        ProductEntity productEntityFromRepo = productRepository.save(product);

        OfferEntity offerEntity = new OfferEntity();
        offerEntity.setFeedId(12234L);
        offerEntity.setOfferId("offerID");
        offerEntity.setProduct(productEntityFromRepo);

        OfferEntity offerEntity2 = new OfferEntity();
        offerEntity2.setFeedId(12235L);
        offerEntity2.setOfferId("offerID2");
        offerEntity2.setProduct(productEntityFromRepo);

        offerRepository.saveAll(List.of(offerEntity, offerEntity2));

        List<OfferEntity> offersEntityList = offerRepository.findByProductId(productEntityFromRepo.getId());
        assertNotNull(offersEntityList);
        assertEquals(2, offersEntityList.size());
    }
}

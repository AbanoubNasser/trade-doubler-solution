package com.services.tradedoubler.product.processorservice.repository;

import com.services.tradedoubler.product.processorservice.model.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, UUID> {

    List<OfferEntity> findByProductId(final UUID productId);
}

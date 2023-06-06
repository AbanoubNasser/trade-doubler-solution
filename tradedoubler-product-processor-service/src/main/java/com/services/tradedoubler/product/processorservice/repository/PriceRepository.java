package com.services.tradedoubler.product.processorservice.repository;

import com.services.tradedoubler.product.processorservice.model.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, UUID> {

    Optional<PriceEntity> findByOfferId(final UUID offerId);
}

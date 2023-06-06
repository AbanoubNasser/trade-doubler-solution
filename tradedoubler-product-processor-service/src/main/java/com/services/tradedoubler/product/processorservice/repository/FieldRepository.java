package com.services.tradedoubler.product.processorservice.repository;

import com.services.tradedoubler.product.processorservice.model.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FieldRepository extends JpaRepository<FieldEntity, UUID> {

    List<FieldEntity> findByProductId(final UUID productId);
}

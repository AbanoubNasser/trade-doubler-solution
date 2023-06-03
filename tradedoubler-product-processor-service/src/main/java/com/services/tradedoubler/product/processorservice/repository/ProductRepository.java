package com.services.tradedoubler.product.processorservice.repository;

import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    
}

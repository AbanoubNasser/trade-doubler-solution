package com.services.tradedoubler.productservice.repository;

import com.services.tradedoubler.productservice.model.ProductsFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsFileRepository extends JpaRepository<ProductsFileEntity, String> {

}

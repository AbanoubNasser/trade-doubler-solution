package com.services.tradedoubler.productservice.repository;

import com.services.tradedoubler.productservice.model.FileStatus;
import com.services.tradedoubler.productservice.model.ProductsFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsFileRepository extends JpaRepository<ProductsFileEntity, String> {

    List<ProductsFileEntity> findByStatus(FileStatus status);
}

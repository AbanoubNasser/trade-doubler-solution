package com.services.tradedoubler.productservice.model;

import com.services.tradedoubler.productservice.base.entity.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "products_files")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductsFileEntity extends Auditable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "fileName")
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FileStatus status = FileStatus.UPLOADED;

    @Column(name = "comment")
    private String comment;

    public ProductsFileEntity(String fileName) {
        this.id = UUID.randomUUID().toString();
        this.fileName = fileName;
    }
}

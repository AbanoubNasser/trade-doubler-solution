package com.services.tradedoubler.product.processorservice.integration.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsFile {

    private String id;

    private String fileName;

    private FileStatus status;

    private String comment;
}

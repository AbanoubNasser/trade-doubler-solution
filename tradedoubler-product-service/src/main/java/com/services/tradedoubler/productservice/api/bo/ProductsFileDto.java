package com.services.tradedoubler.productservice.api.bo;

import com.services.tradedoubler.productservice.model.FileStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductsFileDto {

    private String id;

    private String fileName;

    private FileStatus status;

    private String comment;
}

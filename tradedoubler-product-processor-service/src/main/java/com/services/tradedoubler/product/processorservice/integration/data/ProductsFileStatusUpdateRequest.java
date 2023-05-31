package com.services.tradedoubler.product.processorservice.integration.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductsFileStatusUpdateRequest {

    private FileStatus status;

    private String comment;
}

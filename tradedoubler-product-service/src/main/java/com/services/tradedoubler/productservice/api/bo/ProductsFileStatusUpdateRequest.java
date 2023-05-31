package com.services.tradedoubler.productservice.api.bo;

import com.services.tradedoubler.productservice.model.FileStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsFileStatusUpdateRequest {

    @NotNull
    private FileStatus status;

    private String comment;
}

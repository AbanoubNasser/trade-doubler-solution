package com.services.tradedoubler.productservice.exception;

import lombok.*;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ServiceErrorResponse {

    /**
     * The description.
     */
    private String description;

    /**
     * The error.
     */
    private String error;

    /**
     * The status.
     */
    private String status;
}

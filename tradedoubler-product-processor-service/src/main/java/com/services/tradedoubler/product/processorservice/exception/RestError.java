package com.services.tradedoubler.product.processorservice.exception;

import org.springframework.http.HttpStatus;

public interface RestError {

    String error();

    HttpStatus httpStatus();

    String description();
}

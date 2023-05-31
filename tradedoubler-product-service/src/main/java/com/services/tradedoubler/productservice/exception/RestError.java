package com.services.tradedoubler.productservice.exception;

import org.springframework.http.HttpStatus;

public interface RestError {

    String error();

    HttpStatus httpStatus();

    String description();
}

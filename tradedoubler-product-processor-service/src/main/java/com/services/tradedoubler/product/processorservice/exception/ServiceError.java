package com.services.tradedoubler.product.processorservice.exception;

import org.springframework.http.HttpStatus;

public enum ServiceError implements RestError {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ""),
    REQUEST_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Invalid expected input"),

    ERROR_WHILE_LOAD_SCHEMA_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Schema path"),
    INVALID_XML_CONTENT(HttpStatus.BAD_REQUEST, "Invalid xml content vs defined schema"),

    INVALID_XML_MAPPING(HttpStatus.BAD_REQUEST, "Invalid xml mapping values to schema"),

    ERROR_WHILE_PERSISTING_PRODUCTS_DATA(HttpStatus.INTERNAL_SERVER_ERROR, ""),

    NOT_FOUND_PRODUCT_IMAGE(HttpStatus.NOT_FOUND, "Product image is not found"),

    NOT_FOUND_OFFER_PRICE(HttpStatus.NOT_FOUND, "Product offer price is not found"),

    ERROR_WHILE_PARSING_DOWNLOAD_RESULT(HttpStatus.INTERNAL_SERVER_ERROR, ""),

    NO_PRODUCTS_FOUND_FOR_PRODUCT_FILE_ID(HttpStatus.NOT_FOUND, "No products found with provided product file id");
    /**
     * The http status.
     */
    private HttpStatus httpStatus;
    /**
     * The description.
     */
    private String description;

    /**
     * Instantiates a new Service errors.
     *
     * @param httpStatus  the http status
     * @param description the description
     */
    private ServiceError(final HttpStatus httpStatus, final String description) {
        this.httpStatus = httpStatus;
        this.description = description;
    }

    @Override
    public String error() {
        return this.name();
    }

    @Override
    public HttpStatus httpStatus() {
        return this.httpStatus;
    }

    @Override
    public String description() {
        return this.description;
    }

    public RestException buildException() {
        return new ServiceException(this);
    }

    public RestException buildException(String message) {
        this.description = message;
        return new ServiceException(this);
    }
}

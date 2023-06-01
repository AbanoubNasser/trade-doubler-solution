package com.services.tradedoubler.product.processorservice.exception;

import org.springframework.http.HttpStatus;

public enum ServiceError implements RestError {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ""),
    REQUEST_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Invalid expected input"),

    INVALID_XML_CONTENT(HttpStatus.BAD_REQUEST, "Invalid xml content vs defined schema"),

    INVALID_XML_MAPPING(HttpStatus.BAD_REQUEST, "Invalid xml mapping values to schema"),;
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

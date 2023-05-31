package com.services.tradedoubler.productservice.exception;

import org.springframework.http.HttpStatus;

public enum ServiceError implements RestError {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ""),
    REQUEST_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Invalid expected input"),

    NOT_FOUND_PRODUCTS_FILE(HttpStatus.NOT_FOUND, "Products file with specified criteria is not found"),
    DUPLICATE_FILES_WITH_THE_SAME_NAME(HttpStatus.BAD_REQUEST, "File is already exist"),

    ERROR_NOT_READABLE_FILE(HttpStatus.BAD_REQUEST, "Requested file is unreadable");
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

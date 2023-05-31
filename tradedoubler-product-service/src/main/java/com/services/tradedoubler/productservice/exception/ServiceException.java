package com.services.tradedoubler.productservice.exception;

public class ServiceException extends RestException{

    private ServiceError serviceError;
    /**
     * Instantiates a new Service exception.
     *
     * @param serviceError
     */
    public ServiceException(final ServiceError serviceError) {
        super(serviceError);
        this.serviceError = serviceError;
    }
}

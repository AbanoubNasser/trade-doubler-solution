package com.services.tradedoubler.product.processorservice.exception;

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

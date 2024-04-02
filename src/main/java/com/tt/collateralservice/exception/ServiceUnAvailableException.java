package com.tt.collateralservice.exception;

public class ServiceUnAvailableException extends RuntimeException{
    public ServiceUnAvailableException(String message) {
        super(message);
    }
}

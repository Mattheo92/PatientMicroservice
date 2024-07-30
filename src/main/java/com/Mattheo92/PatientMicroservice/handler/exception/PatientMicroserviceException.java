package com.Mattheo92.PatientMicroservice.handler.exception;

import org.springframework.http.HttpStatus;

public class PatientMicroserviceException extends RuntimeException {
    private org.springframework.http.HttpStatus status;

    public PatientMicroserviceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
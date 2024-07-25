package com.Mattheo92.PatientMicroservice.handler.exception;

import org.springframework.http.HttpStatus;

public class VisitNotFoundException extends PatientMicroserviceException{
    public VisitNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}

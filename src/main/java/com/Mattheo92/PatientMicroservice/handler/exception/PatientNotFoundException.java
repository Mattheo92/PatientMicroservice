package com.Mattheo92.PatientMicroservice.handler.exception;

import org.springframework.http.HttpStatus;

public class PatientNotFoundException extends PatientMicroserviceException{

    public PatientNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}

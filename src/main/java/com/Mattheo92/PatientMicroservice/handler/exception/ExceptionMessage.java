package com.Mattheo92.PatientMicroservice.handler.exception;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionMessage {
@JsonProperty("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.example.ticketmaster.exception;


import org.springframework.http.HttpStatus;

public class ArtistNotFoundException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public ArtistNotFoundException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}


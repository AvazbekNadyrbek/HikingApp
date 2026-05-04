package com.berchtesgaden.explorer.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String field, String value) {
        super(field + "alredy exists: " + value);
    }
}

package com.bancolombia.tallerFinal.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userNoEncotrado) {
        super(userNoEncotrado);
    }
}

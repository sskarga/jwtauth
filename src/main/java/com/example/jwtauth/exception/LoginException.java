package com.example.jwtauth.exception;

public class LoginException extends RuntimeException{
    public LoginException(String message) {
        super(message);
    }
}

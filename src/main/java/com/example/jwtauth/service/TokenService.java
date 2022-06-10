package com.example.jwtauth.service;

public interface TokenService {
    String generateToken(String clientId);
}

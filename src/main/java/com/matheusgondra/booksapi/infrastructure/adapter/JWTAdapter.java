package com.matheusgondra.booksapi.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.matheusgondra.booksapi.application.protocol.cryptography.TokenGenerator;

public class JWTAdapter implements TokenGenerator {
    @Value("${jwt.secret}")
    private String secret;

    public JWTAdapter(String secret) {
        this.secret = secret;
    }
    
    @Override
    public String generate(String payload) {        
        JWT.create();
    
        return null;
    }
}

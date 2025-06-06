package com.matheusgondra.booksapi.application.service;

import org.springframework.stereotype.Service;

import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.usecase.SignUpUseCase;

@Service
public class SignUpService implements SignUpUseCase {
    private final LoadUserByEmailGateway loadUserByEmailGateway;

    public SignUpService(LoadUserByEmailGateway loadUserByEmailGateway) {
        this.loadUserByEmailGateway = loadUserByEmailGateway;
    }

    @Override
    public SignUpResponse signUp(SignUpParam param) {
        this.loadUserByEmailGateway.loadByEmail(param.email());
        
        return null;
    }
}

package com.matheusgondra.booksapi.application.service;

import org.springframework.stereotype.Service;

import com.matheusgondra.booksapi.application.protocol.cryptography.HashGenerator;
import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.exception.UserAlreadyExistsException;
import com.matheusgondra.booksapi.domain.models.User;
import com.matheusgondra.booksapi.domain.usecase.SignUpUseCase;

@Service
public class SignUpService implements SignUpUseCase {
    private final LoadUserByEmailGateway loadUserByEmailGateway;
    private final HashGenerator hashGenerator;

    public SignUpService(LoadUserByEmailGateway loadUserByEmailGateway, HashGenerator hashGenerator) {
        this.loadUserByEmailGateway = loadUserByEmailGateway;
        this.hashGenerator = hashGenerator;
    }

    @Override
    public SignUpResponse signUp(SignUpParam param) {
        User user = this.loadUserByEmailGateway.loadByEmail(param.email());
        if (user != null) {
            throw new UserAlreadyExistsException();
        }
        
        this.hashGenerator.generate(param.password());

        return null;
    }
}

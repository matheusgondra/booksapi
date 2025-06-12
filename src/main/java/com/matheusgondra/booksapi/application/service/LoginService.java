package com.matheusgondra.booksapi.application.service;

import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.exception.InvalidCredentialsException;
import com.matheusgondra.booksapi.domain.usecase.LoginUseCase;

public class LoginService implements LoginUseCase {
    private final LoadUserByEmailGateway loadUserByEmailGateway;

    public LoginService(LoadUserByEmailGateway loadUserByEmailGateway) {
        this.loadUserByEmailGateway = loadUserByEmailGateway;
    }

    @Override
    public String login(LoginRequest request) {
        this.loadUserByEmailGateway.loadByEmail(request.email()).orElseThrow(InvalidCredentialsException::new);
        return null;
    }
}

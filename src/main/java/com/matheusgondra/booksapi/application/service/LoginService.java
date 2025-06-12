package com.matheusgondra.booksapi.application.service;

import com.matheusgondra.booksapi.application.protocol.cryptography.HashCompare;
import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.exception.InvalidCredentialsException;
import com.matheusgondra.booksapi.domain.models.User;
import com.matheusgondra.booksapi.domain.usecase.LoginUseCase;

public class LoginService implements LoginUseCase {
    private final LoadUserByEmailGateway loadUserByEmailGateway;
    private final HashCompare hashCompare;

    public LoginService(LoadUserByEmailGateway loadUserByEmailGateway, HashCompare hashCompare) {
        this.loadUserByEmailGateway = loadUserByEmailGateway;
        this.hashCompare = hashCompare;
    }

    @Override
    public String login(LoginRequest request) {
        User user = this.loadUserByEmailGateway.loadByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        boolean isValidPassword = this.hashCompare.compare(request.password(), user.getPassword());
        if (!isValidPassword) {
            throw new InvalidCredentialsException();
        }

        return null;
    }
}

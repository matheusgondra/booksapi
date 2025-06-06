package com.matheusgondra.booksapi.application.service;

import org.springframework.stereotype.Service;

import com.matheusgondra.booksapi.application.protocol.cryptography.HashGenerator;
import com.matheusgondra.booksapi.application.protocol.gateway.AddUserGateway;
import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.exception.UserAlreadyExistsException;
import com.matheusgondra.booksapi.domain.models.User;
import com.matheusgondra.booksapi.domain.usecase.SignUpUseCase;

@Service
public class SignUpService implements SignUpUseCase {
    private final LoadUserByEmailGateway loadUserByEmailGateway;
    private final AddUserGateway addUserGateway;
    private final HashGenerator hashGenerator;

    public SignUpService(LoadUserByEmailGateway loadUserByEmailGateway, AddUserGateway addUserGateway, HashGenerator hashGenerator) {
        this.loadUserByEmailGateway = loadUserByEmailGateway;
        this.addUserGateway = addUserGateway;
        this.hashGenerator = hashGenerator;
    }

    @Override
    public SignUpResponse signUp(SignUpParam param) {
        this.loadUserByEmailGateway.loadByEmail(param.email()).ifPresent(user -> {
            throw new UserAlreadyExistsException();
        });
        
        String hash = this.hashGenerator.generate(param.password());
        User user = new User(param.firstName(), param.lastName(), param.email(), hash);

        user = this.addUserGateway.add(user);

        return new SignUpResponse(user);
    }
}

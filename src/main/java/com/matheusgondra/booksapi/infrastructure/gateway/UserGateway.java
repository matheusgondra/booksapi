package com.matheusgondra.booksapi.infrastructure.gateway;

import java.util.Optional;

import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.models.User;
import com.matheusgondra.booksapi.infrastructure.repository.UserRepository;

public class UserGateway implements LoadUserByEmailGateway {
    private final UserRepository userRepository;

    public UserGateway(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> loadByEmail(String email) {
        this.userRepository.findByEmail(email);
        return Optional.empty();
    }
}

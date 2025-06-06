package com.matheusgondra.booksapi.application.protocol.gateway;

import java.util.Optional;

import com.matheusgondra.booksapi.domain.models.User;

public interface LoadUserByEmailGateway {
    Optional<User> loadByEmail(String email);
}

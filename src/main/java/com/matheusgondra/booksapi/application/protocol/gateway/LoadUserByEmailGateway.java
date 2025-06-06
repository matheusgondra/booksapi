package com.matheusgondra.booksapi.application.protocol.gateway;

import com.matheusgondra.booksapi.domain.models.User;

public interface LoadUserByEmailGateway {
    User loadByEmail(String email);
}

package com.matheusgondra.booksapi.application.protocol.gateway;

import com.matheusgondra.booksapi.domain.models.User;

public interface AddUserGateway {
    User add(User user);
}

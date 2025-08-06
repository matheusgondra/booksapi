package com.matheusgondra.booksapi.application.protocol.gateway;

import com.matheusgondra.booksapi.domain.models.User;
import java.util.Optional;

public interface LoadUserByEmailGateway {
  Optional<User> loadByEmail(String email);
}

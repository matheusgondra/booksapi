package com.matheusgondra.booksapi.application.protocol.gateway;

import com.matheusgondra.booksapi.domain.models.Author;
import java.util.Optional;

public interface LoadAuthorByNameGateway {
  Optional<Author> loadByName(String name);
}

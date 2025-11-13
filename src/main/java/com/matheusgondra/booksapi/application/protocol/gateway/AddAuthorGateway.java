package com.matheusgondra.booksapi.application.protocol.gateway;

import com.matheusgondra.booksapi.domain.models.Author;

public interface AddAuthorGateway {
    Author add(Author author);
}

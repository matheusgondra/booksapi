package com.matheusgondra.booksapi.application.service.author;

import com.matheusgondra.booksapi.application.protocol.gateway.AddAuthorGateway;
import com.matheusgondra.booksapi.application.protocol.gateway.LoadAuthorByNameGateway;
import com.matheusgondra.booksapi.domain.exception.AuthorAlreadyExistsException;
import com.matheusgondra.booksapi.domain.models.Author;
import com.matheusgondra.booksapi.domain.usecase.author.RegisterAuthorUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterAuthorService implements RegisterAuthorUseCase {
  private final LoadAuthorByNameGateway loadAuthorByNameGateway;
  private final AddAuthorGateway addAuthorGateway;

  @Override
  public RegisterAuthorResponse register(RegisterAuthorParam param) {
    this.loadAuthorByNameGateway.loadByName(param.name()).ifPresent(author -> {
      throw new AuthorAlreadyExistsException();
    });

    Author author = new Author(param.name());
    this.addAuthorGateway.add(author);

    return new RegisterAuthorResponse(author);
  }
}

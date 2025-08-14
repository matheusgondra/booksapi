package com.matheusgondra.booksapi.application.service.author;

import com.matheusgondra.booksapi.application.protocol.gateway.LoadAuthorByNameGateway;
import com.matheusgondra.booksapi.domain.usecase.author.RegisterAuthorUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterAuthorService implements RegisterAuthorUseCase {
  private final LoadAuthorByNameGateway loadAuthorByNameGateway;

  @Override
  public RegisterAuthorResponse register(RegisterAuthorParam param) {
    this.loadAuthorByNameGateway.loadByName(param.name());

    return null;
  }
}

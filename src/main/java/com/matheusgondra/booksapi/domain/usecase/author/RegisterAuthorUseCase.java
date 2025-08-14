package com.matheusgondra.booksapi.domain.usecase.author;

import com.matheusgondra.booksapi.domain.models.Author;

public interface RegisterAuthorUseCase {
  RegisterAuthorResponse register(RegisterAuthorParam param);

  public record RegisterAuthorParam(String name) {}

  public record RegisterAuthorResponse(Author author) {}
}

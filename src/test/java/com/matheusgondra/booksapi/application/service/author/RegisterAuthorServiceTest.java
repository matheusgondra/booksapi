package com.matheusgondra.booksapi.application.service.author;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.matheusgondra.booksapi.application.protocol.gateway.LoadAuthorByNameGateway;
import com.matheusgondra.booksapi.domain.exception.AuthorAlreadyExistsException;
import com.matheusgondra.booksapi.domain.models.Author;
import com.matheusgondra.booksapi.domain.usecase.author.RegisterAuthorUseCase.RegisterAuthorParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegisterAuthorServiceTest {
  @InjectMocks RegisterAuthorService sut;

  @Mock LoadAuthorByNameGateway loadAuthorByNameGateway;

  RegisterAuthorParam registerAuthorParam = new RegisterAuthorParam("anyName");
  Author author = new Author("anyName");

  @Test
  @DisplayName("Should call LoadAuthorByNameGateway with correct value")
  void case01() {
    sut.register(registerAuthorParam);

    verify(loadAuthorByNameGateway).loadByName(registerAuthorParam.name());
  }

  @Test
  @DisplayName("Should throw AuthorAlreadyExistsException when LoadAuthorByNameGateway returns an author")
  void case02() {
    when(loadAuthorByNameGateway.loadByName("anyName")).thenReturn(Optional.of(author));

    assertThrows(AuthorAlreadyExistsException.class, () -> sut.register(registerAuthorParam));
  }
}

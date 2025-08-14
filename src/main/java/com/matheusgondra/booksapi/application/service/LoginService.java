package com.matheusgondra.booksapi.application.service;

import com.matheusgondra.booksapi.application.protocol.cryptography.HashCompare;
import com.matheusgondra.booksapi.application.protocol.cryptography.TokenGenerator;
import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.exception.InvalidCredentialsException;
import com.matheusgondra.booksapi.domain.models.User;
import com.matheusgondra.booksapi.domain.usecase.auth.LoginUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginService implements LoginUseCase {
  private final LoadUserByEmailGateway loadUserByEmailGateway;
  private final HashCompare hashCompare;
  private final TokenGenerator tokenGenerator;

  @Override
  public String login(LoginRequest request) {
    User user =
        this.loadUserByEmailGateway
            .loadByEmail(request.email())
            .orElseThrow(InvalidCredentialsException::new);

    boolean isValidPassword = this.hashCompare.compare(request.password(), user.getPassword());
    if (!isValidPassword) {
      throw new InvalidCredentialsException();
    }

    String accessToken = this.tokenGenerator.generate(user.getId().toString());

    return accessToken;
  }
}

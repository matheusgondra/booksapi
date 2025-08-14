package com.matheusgondra.booksapi.domain.usecase.auth;

public interface LoginUseCase {
  String login(LoginRequest request);

  record LoginRequest(String email, String password) {}
}

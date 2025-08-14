package com.matheusgondra.booksapi.domain.usecase.auth;

import com.matheusgondra.booksapi.domain.models.User;

public interface SignUpUseCase {
  SignUpResponse signUp(SignUpParam param);

  record SignUpParam(String firstName, String lastName, String email, String password) {}

  record SignUpResponse(User user) {}
}

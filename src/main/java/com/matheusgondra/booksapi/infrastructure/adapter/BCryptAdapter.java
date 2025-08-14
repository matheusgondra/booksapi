package com.matheusgondra.booksapi.infrastructure.adapter;

import com.matheusgondra.booksapi.application.protocol.cryptography.HashCompare;
import com.matheusgondra.booksapi.application.protocol.cryptography.HashGenerator;

import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class BCryptAdapter implements HashGenerator, HashCompare {
  private final PasswordEncoder encoder;

  @Override
  public String generate(String value) {
    return this.encoder.encode(value);
  }

  @Override
  public boolean compare(String rawValue, String hashedValue) {
    return this.encoder.matches(rawValue, hashedValue);
  }
}

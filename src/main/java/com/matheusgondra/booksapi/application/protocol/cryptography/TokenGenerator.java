package com.matheusgondra.booksapi.application.protocol.cryptography;

public interface TokenGenerator {
  String generate(String payload);
}

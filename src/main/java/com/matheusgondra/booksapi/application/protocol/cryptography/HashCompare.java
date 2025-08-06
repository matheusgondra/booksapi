package com.matheusgondra.booksapi.application.protocol.cryptography;

public interface HashCompare {
  boolean compare(String rawValue, String hashedValue);
}

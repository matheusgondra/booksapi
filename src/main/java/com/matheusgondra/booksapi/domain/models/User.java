package com.matheusgondra.booksapi.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public User(String firstName, String lastName, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }
}

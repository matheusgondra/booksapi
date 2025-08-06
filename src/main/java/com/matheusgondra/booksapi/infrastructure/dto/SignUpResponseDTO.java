package com.matheusgondra.booksapi.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matheusgondra.booksapi.domain.models.User;
import java.time.LocalDateTime;
import java.util.UUID;

public record SignUpResponseDTO(
    UUID id,
    @JsonProperty("first_name") String firstName,
    @JsonProperty("last_name") String lastName,
    String email,
    @JsonProperty("created_at") LocalDateTime createdAt,
    @JsonProperty("updated_at") LocalDateTime updatedAt) {
  public SignUpResponseDTO(User user) {
    this(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getCreatedAt(),
        user.getUpdatedAt());
  }
}

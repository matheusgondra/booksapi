package com.matheusgondra.booksapi.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SignUpRequestDTO(
    @Schema(description = "User's first name", example = "John")
        @NotBlank
        @JsonProperty("first_name")
        String firstName,
    @Schema(description = "User's last name", example = "Doe") @NotBlank @JsonProperty("last_name")
        String lastName,
    @Schema(description = "User's email address", example = "johndoe@email.com") @Email
        String email,
    @Schema(description = "User's password", example = "StrongP@ssw0rd")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
        @NotBlank
        String password) {}

package com.matheusgondra.booksapi.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequestDTO(
    @Schema(description = "User's email address", example = "johndoe@email.com")
    @Email
    @NotBlank 
    String email,
   
    @Schema(description = "User's password", example = "StrongP@ssw0rd")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$") 
    @NotBlank
    String password) {
}

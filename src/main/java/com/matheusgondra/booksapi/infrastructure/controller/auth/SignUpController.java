package com.matheusgondra.booksapi.infrastructure.controller.auth;

import com.matheusgondra.booksapi.domain.usecase.auth.SignUpUseCase;
import com.matheusgondra.booksapi.domain.usecase.auth.SignUpUseCase.SignUpParam;
import com.matheusgondra.booksapi.domain.usecase.auth.SignUpUseCase.SignUpResponse;
import com.matheusgondra.booksapi.infrastructure.doc.annotations.ApiBadRequest;
import com.matheusgondra.booksapi.infrastructure.doc.annotations.ApiConflict;
import com.matheusgondra.booksapi.infrastructure.doc.annotations.ApiInternalServerError;
import com.matheusgondra.booksapi.infrastructure.dto.SignUpRequestDTO;
import com.matheusgondra.booksapi.infrastructure.dto.SignUpResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/api/signup")
@AllArgsConstructor
public class SignUpController {
  private final SignUpUseCase useCase;

  @Operation(summary = "Sign up a new user")
  @ApiResponse(
      responseCode = "201",
      description = "User successfully signed up",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = SignUpResponseDTO.class)))
  @ApiBadRequest
  @ApiInternalServerError
  @ApiConflict
  @PostMapping
  public ResponseEntity<SignUpResponseDTO> handle(@RequestBody @Valid SignUpRequestDTO dto) {
    SignUpResponse result =
        this.useCase.signUp(
            new SignUpParam(dto.firstName(), dto.lastName(), dto.email(), dto.password()));
    return ResponseEntity.created(null).body(new SignUpResponseDTO(result.user()));
  }
}

package com.matheusgondra.booksapi.infrastructure.controller.auth;

import com.matheusgondra.booksapi.domain.usecase.auth.LoginUseCase;
import com.matheusgondra.booksapi.domain.usecase.auth.LoginUseCase.LoginRequest;
import com.matheusgondra.booksapi.infrastructure.dto.LoginRequestDTO;
import com.matheusgondra.booksapi.infrastructure.dto.LoginResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
public class LoginController {
  private final LoginUseCase useCase;

  @PostMapping
  public ResponseEntity<LoginResponseDTO> handle(@RequestBody @Valid LoginRequestDTO dto) {
    String accessToken = this.useCase.login(new LoginRequest(dto.email(), dto.password()));
    return ResponseEntity.ok(new LoginResponseDTO(accessToken));
  }
}

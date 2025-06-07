package com.matheusgondra.booksapi.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusgondra.booksapi.domain.usecase.SignUpUseCase;
import com.matheusgondra.booksapi.domain.usecase.SignUpUseCase.SignUpParam;
import com.matheusgondra.booksapi.domain.usecase.SignUpUseCase.SignUpResponse;
import com.matheusgondra.booksapi.infrastructure.dto.SignUpRequestDTO;
import com.matheusgondra.booksapi.infrastructure.dto.SignUpResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/signup")
public class SignUpController {
	private final SignUpUseCase useCase;

	public SignUpController(SignUpUseCase useCase) {
		this.useCase = useCase;
	}

	@PostMapping
	public ResponseEntity<SignUpResponseDTO> handle(@RequestBody @Valid SignUpRequestDTO dto) {
		SignUpResponse result = this.useCase
				.signUp(new SignUpParam(dto.firstName(), dto.lastName(), dto.email(), dto.password()));

		return ResponseEntity.created(null).body(new SignUpResponseDTO(result.user()));
	}
}

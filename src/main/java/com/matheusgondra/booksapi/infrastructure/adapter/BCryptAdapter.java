package com.matheusgondra.booksapi.infrastructure.adapter;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.matheusgondra.booksapi.application.protocol.cryptography.HashGenerator;

public class BCryptAdapter implements HashGenerator {
	private final PasswordEncoder encoder;

	public BCryptAdapter(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	@Override
	public String generate(String value) {
		return this.encoder.encode(value);
	}
}

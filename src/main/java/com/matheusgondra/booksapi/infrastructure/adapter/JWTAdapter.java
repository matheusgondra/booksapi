package com.matheusgondra.booksapi.infrastructure.adapter;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.matheusgondra.booksapi.application.protocol.cryptography.TokenGenerator;

public class JWTAdapter implements TokenGenerator {
	@Value("${jwt.secret}")
	private String secret;

	public JWTAdapter(String secret) {
		this.secret = secret;
	}

	@Override
	public String generate(String payload) {
		Duration oneDay = Duration.ofDays(1);
		Instant expiresAt = Instant.now().plus(oneDay);

		JWT.create()
				.withExpiresAt(expiresAt);

		return null;
	}
}

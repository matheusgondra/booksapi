package com.matheusgondra.booksapi.infrastructure.adapter;

import java.time.Duration;
import java.time.Instant;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.matheusgondra.booksapi.application.protocol.cryptography.TokenGenerator;

public class JWTAdapter implements TokenGenerator {
	private String secret;

	public JWTAdapter(String secret) {
		this.secret = secret;
	}

	@Override
	public String generate(String payload) {
		Duration oneDay = Duration.ofDays(1);
		Instant expiresAt = Instant.now().plus(oneDay);

		Algorithm algorithm = Algorithm.HMAC256(this.secret);

		String accessToken = JWT.create()
				.withExpiresAt(expiresAt)
				.withSubject(payload)
				.sign(algorithm);

		return accessToken;
	}
}

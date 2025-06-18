package com.matheusgondra.booksapi.infrastructure.adapter;

import java.time.Duration;
import java.time.Instant;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.matheusgondra.booksapi.application.protocol.cryptography.TokenDecode;
import com.matheusgondra.booksapi.application.protocol.cryptography.TokenGenerator;

public class JWTAdapter implements TokenGenerator, TokenDecode {
	private final Algorithm algorithm;

	public JWTAdapter(String secret) {
		this.algorithm = Algorithm.HMAC256(secret);
	}

	@Override
	public String generate(String payload) {
		Duration oneDay = Duration.ofDays(1);
		Instant expiresAt = Instant.now().plus(oneDay);

		String accessToken = JWT.create()
				.withExpiresAt(expiresAt)
				.withSubject(payload)
				.sign(algorithm);

		return accessToken;
	}

	@Override
	public String decode(String token) {
		JWT.require(algorithm);
		
		return null;
	}
}

package com.matheusgondra.booksapi.infrastructure.adapter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.matheusgondra.booksapi.application.protocol.cryptography.TokenDecode;
import com.matheusgondra.booksapi.application.protocol.cryptography.TokenGenerator;
import java.time.Duration;
import java.time.Instant;

public class JWTAdapter implements TokenGenerator, TokenDecode {
  private final Algorithm algorithm;

  public JWTAdapter(String secret) {
    this.algorithm = Algorithm.HMAC256(secret);
  }

  @Override
  public String generate(String payload) {
    Duration oneDay = Duration.ofDays(1);
    Instant expiresAt = Instant.now().plus(oneDay);

    String accessToken = JWT.create().withExpiresAt(expiresAt).withSubject(payload).sign(algorithm);

    return accessToken;
  }

  @Override
  public String decode(String token) {
    try {
      DecodedJWT tokenDecoded = JWT.require(algorithm).build().verify(token);

      return tokenDecoded.getSubject();
    } catch (Exception ex) {
      return null;
    }
  }
}

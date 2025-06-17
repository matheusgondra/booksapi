package com.matheusgondra.booksapi.infrastructure.adapter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

@ExtendWith(MockitoExtension.class)
public class JWTAdapterTest {
	private JWTAdapter sut;
	private String secret = "testSecret";
	private String payload = "testPayload";
	private JWTCreator.Builder mockBuilder = mock(JWTCreator.Builder.class);

	@BeforeEach
	void setup() {
		sut = new JWTAdapter(secret);
	}

	@Test
	@DisplayName("Should call JWT.create() when generating a token")
	void case01() {
		try (MockedStatic<JWT> mockedJWT = mockStatic(JWT.class)) {
			this.setReturnsForMockedJWT(mockedJWT);

			sut.generate(payload);

			mockedJWT.verify(JWT::create);
		}
	}

	@Test
	@DisplayName("Should set expiration time 1 day from now when generating a token")
	void case02() {
		try (MockedStatic<JWT> mockedJWT = mockStatic(JWT.class)) {
			this.setReturnsForMockedJWT(mockedJWT);

			ArgumentCaptor<Instant> captor = ArgumentCaptor.forClass(Instant.class);

			sut.generate(payload);

			verify(mockBuilder).withExpiresAt(captor.capture());

			Instant capturedInstant = captor.getValue();
			Instant expectedInstant = Instant.now().plus(Duration.ofDays(1));

			assertTrue(Duration.between(expectedInstant, capturedInstant).abs().toMillis() < 1000);
		}
	}

	@Test
	@DisplayName("Should set subject to payload when generating a token")
	void case03() {
		try (MockedStatic<JWT> mockedJWT = mockStatic(JWT.class)) {
			this.setReturnsForMockedJWT(mockedJWT);

			sut.generate(payload);

			verify(mockBuilder).withSubject(payload);
		}
	}

	@Test
	@DisplayName("Should sign the token with the secret when generating a token")
	void case04() {
		try (MockedStatic<JWT> mockedJWT = mockStatic(JWT.class)) {
			this.setReturnsForMockedJWT(mockedJWT);

			ArgumentCaptor<Algorithm> captorAlgorithm = ArgumentCaptor.forClass(Algorithm.class);

			sut.generate(payload);

			verify(mockBuilder).sign(captorAlgorithm.capture());
			Algorithm capturedAlgorithm = captorAlgorithm.getValue();
			Algorithm expectedAlgorithm = Algorithm.HMAC256(secret);

			assertTrue(capturedAlgorithm.getName().equals(expectedAlgorithm.getName()));

		}
	}

	private void setReturnsForMockedJWT(MockedStatic<JWT> mockedJWT) {
		mockedJWT.when(JWT::create).thenReturn(mockBuilder);
		mockedJWT.when(() -> mockBuilder.withExpiresAt(any(Instant.class))).thenReturn(mockBuilder);
		mockedJWT.when(() -> mockBuilder.withSubject(any(String.class))).thenReturn(mockBuilder);
	}
}

package com.matheusgondra.booksapi.infrastructure.adapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

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
            mockedJWT.when(JWT::create).thenReturn(mockBuilder);

            sut.generate(payload);

            mockedJWT.verify(JWT::create);
        }
    }

    @Test
    @DisplayName("Should set expiration time 1 day from now when generating a token")
    void case02() {
        try (MockedStatic<JWT> mockedJWT = mockStatic(JWT.class)) {
            mockedJWT.when(JWT::create).thenReturn(mockBuilder);
            mockedJWT.when(() -> mockBuilder.withExpiresAt(any(Instant.class))).thenReturn(mockBuilder);

            ArgumentCaptor<Instant> captor = ArgumentCaptor.forClass(Instant.class);

            sut.generate(payload);

            verify(mockBuilder).withExpiresAt(captor.capture());
        }
    }
}

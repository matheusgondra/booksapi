package com.matheusgondra.booksapi.infrastructure.adapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    public void case01() {
        try (MockedStatic<JWT> mockedJWT = mockStatic(JWT.class)) {
            mockedJWT.when(JWT::create).thenReturn(mockBuilder);

            sut.generate(payload);

            mockedJWT.verify(JWT::create);
        }
    }
}

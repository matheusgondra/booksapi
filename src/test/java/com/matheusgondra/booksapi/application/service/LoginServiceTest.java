package com.matheusgondra.booksapi.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.exception.InvalidCredentialsException;
import com.matheusgondra.booksapi.domain.models.User;
import com.matheusgondra.booksapi.domain.usecase.LoginUseCase.LoginRequest;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @InjectMocks
    private LoginService sut;

    @Mock
    private LoadUserByEmailGateway loadUserByEmailGateway;

    private final LoginRequest request = new LoginRequest("any@email.com", "anyPassword");
    private final User userMock = new User(
            UUID.randomUUID(),
            "anyFirstName",
            "anyLastName",
            request.email(),
            request.password(),
            null,
            null);

    @BeforeEach
    void setup() {
        lenient().when(loadUserByEmailGateway.loadByEmail(request.email())).thenReturn(Optional.of(userMock));
    }

    @Test
    @DisplayName("Should call LoadUserByEmailGateway with correct value")
    void case01() {
        sut.login(request);

        verify(loadUserByEmailGateway).loadByEmail(request.email());
    }

    @Test
    @DisplayName("Should throw if LoadUserByEmailGateway throws")
    void case02() {
        when(loadUserByEmailGateway.loadByEmail(request.email())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> sut.login(request));
    }

    @Test
    @DisplayName("Should throw InvalidCredentialsException if LoadUserByEmailGateway returns empty")
    void case03() {
        when(loadUserByEmailGateway.loadByEmail(request.email())).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> sut.login(request));
    }
}

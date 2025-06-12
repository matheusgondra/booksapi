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

import com.matheusgondra.booksapi.application.protocol.cryptography.HashCompare;
import com.matheusgondra.booksapi.application.protocol.cryptography.TokenGenerator;
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
    
    @Mock
    private TokenGenerator tokenGenerator;

    @Mock
    private HashCompare hashCompare;

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
        lenient().when(hashCompare.compare(request.password(), userMock.getPassword())).thenReturn(true);
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

    @Test
    @DisplayName("Should call HashCompare with correct values")
    void case04() {
        sut.login(request);

        verify(hashCompare).compare(request.password(), userMock.getPassword());
    }

    @Test
    @DisplayName("Should throw InvalidCredentialsException if HashCompare returns false")
    void case05() {
        when(hashCompare.compare(request.password(), userMock.getPassword())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> sut.login(request));
    }

    @Test
    @DisplayName("Should throw if HashCompare throws")
    void case06() {
        when(hashCompare.compare(request.password(), userMock.getPassword())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> sut.login(request));
    }

    @Test
    @DisplayName("Should call TokenGenerator with correct value")
    void case07() {
        sut.login(request);

        verify(tokenGenerator).generate(userMock.getId().toString());
    }

    @Test
    @DisplayName("Should throw if TokenGenerator throws")
    void case08() {
        when(tokenGenerator.generate(userMock.getId().toString())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> sut.login(request));
    }
}

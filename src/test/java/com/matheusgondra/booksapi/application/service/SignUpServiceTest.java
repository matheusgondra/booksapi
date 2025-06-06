package com.matheusgondra.booksapi.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matheusgondra.booksapi.application.protocol.cryptography.HashGenerator;
import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.exception.UserAlreadyExistsException;
import com.matheusgondra.booksapi.domain.models.User;
import com.matheusgondra.booksapi.domain.usecase.SignUpUseCase.SignUpParam;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {
    @InjectMocks
    private SignUpService sut;

    @Mock
    private LoadUserByEmailGateway loadUserByEmailGateway;

    @Mock
    private HashGenerator hashGenerator;

    private final String emailMock = "any@email.com";
    private final SignUpParam signupParamMock = new SignUpParam(
        "anyFirstName",
        "anyLastName",
        emailMock,
        "anyPassword");
    private final User userMock = new User(
        UUID.randomUUID(),
        signupParamMock.firstName(),
        signupParamMock.lastName(),
        signupParamMock.email(),
        signupParamMock.password(),
        LocalDateTime.now(),
        LocalDateTime.now());

    @Test
    @DisplayName("should call loadUserByEmailGateway with correct param")
    void case01() {
        sut.signUp(signupParamMock);

        verify(loadUserByEmailGateway).loadByEmail(emailMock);
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException if user already exists")
    void case02() {
        when(loadUserByEmailGateway.loadByEmail(emailMock)).thenReturn(userMock);

        assertThrows(UserAlreadyExistsException.class, () -> sut.signUp(signupParamMock));
    }

    @Test
    @DisplayName("Should call HashGenerator with correct param")
    void case03() {
        sut.signUp(signupParamMock);

        verify(hashGenerator).generate(signupParamMock.password());
    }
}

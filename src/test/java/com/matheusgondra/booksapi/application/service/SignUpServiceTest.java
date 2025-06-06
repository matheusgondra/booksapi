package com.matheusgondra.booksapi.application.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.usecase.SignUpUseCase.SignUpParam;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {
    @InjectMocks
    private SignUpService sut;

    @Mock
    private LoadUserByEmailGateway loadUserByEmailGateway;

    private final String emailMock = "any@email.com";
    private final SignUpParam signupParamMock = new SignUpParam(
        "anyFirstName",
        "anyLastName",
        emailMock,
        "anyPassword");

    @Test
    @DisplayName("should call loadUserByEmailGateway with correct param")
    void case01() {
        sut.signUp(signupParamMock);

        verify(loadUserByEmailGateway).loadByEmail(emailMock);
    }
}

package com.matheusgondra.booksapi.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.matheusgondra.booksapi.application.protocol.cryptography.HashGenerator;
import com.matheusgondra.booksapi.application.protocol.gateway.AddUserGateway;
import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.domain.exception.UserAlreadyExistsException;
import com.matheusgondra.booksapi.domain.models.User;
import com.matheusgondra.booksapi.domain.usecase.auth.SignUpUseCase.SignUpParam;
import com.matheusgondra.booksapi.domain.usecase.auth.SignUpUseCase.SignUpResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {
  @InjectMocks private SignUpService sut;

  @Mock private LoadUserByEmailGateway loadUserByEmailGateway;

  @Mock private AddUserGateway addUserGateway;

  @Mock private HashGenerator hashGenerator;

  private final String emailMock = "any@email.com";
  private final SignUpParam signupParamMock =
      new SignUpParam("anyFirstName", "anyLastName", emailMock, "anyPassword");
  private final User userMock =
      new User(
          UUID.randomUUID(),
          signupParamMock.firstName(),
          signupParamMock.lastName(),
          signupParamMock.email(),
          signupParamMock.password(),
          LocalDateTime.now(),
          LocalDateTime.now());
  private final User userCreated =
      new User(
          UUID.randomUUID(),
          signupParamMock.firstName(),
          signupParamMock.lastName(),
          signupParamMock.email(),
          "hashedPassword",
          LocalDateTime.now(),
          LocalDateTime.now());
  private final SignUpResponse signUpResponseMock = new SignUpResponse(userCreated);

  @BeforeEach
  void setup() {
    lenient().when(loadUserByEmailGateway.loadByEmail(emailMock)).thenReturn(Optional.empty());
    lenient().when(hashGenerator.generate(signupParamMock.password())).thenReturn("hashedPassword");
    lenient().when(addUserGateway.add(any(User.class))).thenReturn(userCreated);
  }

  @Test
  @DisplayName("should call loadUserByEmailGateway with correct param")
  void case01() {
    sut.signUp(signupParamMock);

    verify(loadUserByEmailGateway).loadByEmail(emailMock);
  }

  @Test
  @DisplayName("Should throw UserAlreadyExistsException if user already exists")
  void case02() {
    when(loadUserByEmailGateway.loadByEmail(emailMock)).thenReturn(Optional.of(userMock));

    assertThrows(UserAlreadyExistsException.class, () -> sut.signUp(signupParamMock));
  }

  @Test
  @DisplayName("Should call HashGenerator with correct param")
  void case03() {
    sut.signUp(signupParamMock);

    verify(hashGenerator).generate(signupParamMock.password());
  }

  @Test
  @DisplayName("Should throw if LoadUserByEmailGateway throws")
  void case04() {
    when(loadUserByEmailGateway.loadByEmail(emailMock)).thenThrow(new RuntimeException());

    assertThrows(RuntimeException.class, () -> sut.signUp(signupParamMock));
  }

  @Test
  @DisplayName("Should throw if HashGenerator throws")
  void case05() {
    when(hashGenerator.generate(signupParamMock.password())).thenThrow(new RuntimeException());

    assertThrows(RuntimeException.class, () -> sut.signUp(signupParamMock));
  }

  @Test
  @DisplayName("Should call AddUserGateway with correct param")
  void case06() {
    sut.signUp(signupParamMock);

    User expectedUser =
        new User(
            signupParamMock.firstName(),
            signupParamMock.lastName(),
            signupParamMock.email(),
            "hashedPassword");

    verify(addUserGateway).add(expectedUser);
  }

  @Test
  @DisplayName("Should throw if AddUserGateway throws")
  void case07() {
    when(addUserGateway.add(any(User.class))).thenThrow(RuntimeException.class);

    assertThrows(RuntimeException.class, () -> sut.signUp(signupParamMock));
  }

  @Test
  @DisplayName("Should return a SignUpResponse on success")
  void case08() {
    SignUpResponse result = sut.signUp(signupParamMock);

    assert result.equals(signUpResponseMock);
  }
}

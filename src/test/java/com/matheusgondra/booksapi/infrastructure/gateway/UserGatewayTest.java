package com.matheusgondra.booksapi.infrastructure.gateway;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matheusgondra.booksapi.infrastructure.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserGatewayTest {
    @InjectMocks
    private UserGateway sut;

    @Mock
    private UserRepository userRepository;

    @Nested
    class LoadUserByEmailGatewayTests {
        private final String email = "any@email.com";
        
        @Test
        @DisplayName("Should call UserRepository.findByEmail with correct valid")
        void case01() {
            sut.loadByEmail(email);

            verify(userRepository).findByEmail(email);
        }
    }
}

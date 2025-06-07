package com.matheusgondra.booksapi.infrastructure.gateway;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matheusgondra.booksapi.domain.models.User;
import com.matheusgondra.booksapi.infrastructure.entity.UserEntity;
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
		private final UserEntity userEntityMock = new UserEntity(
				UUID.randomUUID(),
				"anyFirstName",
				"anyLastName",
				email,
				"anyPassword",
				LocalDateTime.now(),
				LocalDateTime.now());

		@BeforeEach
		void setup() {
			lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntityMock));
		}

		@Test
		@DisplayName("Should call UserRepository.findByEmail with correct valid")
		void case01() {
			sut.loadByEmail(email);

			verify(userRepository).findByEmail(email);
		}

		@Test
		@DisplayName("Should return empty Optional if UserRepository.findByEmail returns null")
		void case02() {
			when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

			Optional<User> result = sut.loadByEmail(email);

			assert result.isEmpty();
		}

		@Test
		@DisplayName("Should return Optional User if UserRepository.findByEmail returns a User")
		void case03() {
			Optional<User> result = sut.loadByEmail(email);
			User user = result.get();

			assert result.isPresent();
			assert user.getId().equals(userEntityMock.getId());
			assert user.getFirstName().equals(userEntityMock.getFirstName());
			assert user.getLastName().equals(userEntityMock.getLastName());
			assert user.getEmail().equals(userEntityMock.getEmail());
			assert user.getPassword().equals(userEntityMock.getPassword());
			assert user.getCreatedAt().equals(userEntityMock.getCreatedAt());
			assert user.getUpdatedAt().equals(userEntityMock.getUpdatedAt());
		}
	}
}

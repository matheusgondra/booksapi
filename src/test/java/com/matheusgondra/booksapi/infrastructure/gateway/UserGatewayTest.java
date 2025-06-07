package com.matheusgondra.booksapi.infrastructure.gateway;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.matheusgondra.booksapi.infrastructure.mapper.UserMapper;
import com.matheusgondra.booksapi.infrastructure.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserGatewayTest {
	@InjectMocks
	private UserGateway sut;

	@Mock
	private UserRepository userRepository;

	private final String email = "any@email.com";
	private final UserEntity userEntityMock = new UserEntity(
			UUID.randomUUID(),
			"anyFirstName",
			"anyLastName",
			email,
			"anyPassword",
			LocalDateTime.now(),
			LocalDateTime.now());
	private final User userMock = new User("anyFirstName", "anyLastName", email, "anyPassword");

	@BeforeEach
	void setup() {
		lenient().when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntityMock));
		lenient().when(userRepository.save(UserMapper.toEntity(userMock))).thenReturn(userEntityMock);
	}

	@Nested
	class LoadUserByEmailGatewayTests {
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

	@Nested
	class AddUserByGatewayTests {
		@Test
		@DisplayName("Should call UserRepository.save with correct value")
		void case01() {
			sut.add(userMock);

			verify(userRepository).save(UserMapper.toEntity(userMock));
		}

		@Test
		@DisplayName("Should throw if UserRepository.save throws an exception")
		void case02() {
			when(userRepository.save(UserMapper.toEntity(userMock))).thenThrow(new RuntimeException());

			assertThrows(RuntimeException.class, () -> sut.add(userMock));
		}

		@Test
		@DisplayName("Should return User on success")
		void case03() {
			User result = sut.add(userMock);

			assert result.equals(UserMapper.toDomain(userEntityMock));
		}
	}
}

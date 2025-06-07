package com.matheusgondra.booksapi.infrastructure.adapter;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class BCryptAdapterTest {
	@InjectMocks
	private BCryptAdapter sut;

	@Mock
	private PasswordEncoder encoder;

	@Test
	@DisplayName("Should call PasswordEncoder.encode with correct value")
	void case01() {
		String value = "anyValue";
		sut.generate(value);

		verify(encoder).encode(value);
	}
}

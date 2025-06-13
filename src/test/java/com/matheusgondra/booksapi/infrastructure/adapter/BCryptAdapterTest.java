package com.matheusgondra.booksapi.infrastructure.adapter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

	private final String value = "anyValue";
	private final String hashedValue = "hashedValue";

	@Nested
	class HashGeneratorTests {
		@Test
		@DisplayName("Should call PasswordEncoder.encode with correct value")
		void case01() {
			sut.generate(value);
	
			verify(encoder).encode(value);
		}
	
		@Test
		@DisplayName("Should throw if PasswordEncoder.encode throws")
		void case02() {
			when(encoder.encode(value)).thenThrow(new RuntimeException("Error encoding"));
	
			assertThrows(RuntimeException.class, () -> sut.generate("anyValue"));
		}
	
		@Test
		@DisplayName("Should return a hash on success")
		void case03() {
			when(encoder.encode(value)).thenReturn(hashedValue);

			String result = sut.generate(value);

			assert result.equals(hashedValue);
		}
	}


	@Nested
	class HashCompareTests {
		@Test
		@DisplayName("Should call PasswordEncoder.matches with correct values")
		void case01() {
			sut.compare(value, hashedValue);

			verify(encoder).matches(value, hashedValue);
		}

		@Test
		@DisplayName("Should throw if PasswordEncoder.matches throws")
		void case02() {
			when(encoder.matches(value, hashedValue)).thenThrow(new RuntimeException());

			assertThrows(RuntimeException.class, () -> sut.compare(value, hashedValue));
		}
	}
}

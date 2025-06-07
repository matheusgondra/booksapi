package com.matheusgondra.booksapi.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.matheusgondra.booksapi.application.protocol.cryptography.HashGenerator;
import com.matheusgondra.booksapi.application.protocol.gateway.AddUserGateway;
import com.matheusgondra.booksapi.application.protocol.gateway.LoadUserByEmailGateway;
import com.matheusgondra.booksapi.application.service.SignUpService;
import com.matheusgondra.booksapi.infrastructure.adapter.BCryptAdapter;
import com.matheusgondra.booksapi.infrastructure.gateway.UserGateway;
import com.matheusgondra.booksapi.infrastructure.repository.UserRepository;

@Configuration
public class BeanConfig {
	@Bean
	UserGateway userGateway(UserRepository repository) {
		return new UserGateway(repository);
	}

	@Bean
	SignUpService signUpService(LoadUserByEmailGateway loadUserByEmailGateway, AddUserGateway addUserGateway,
			HashGenerator hashGenerator) {
		return new SignUpService(loadUserByEmailGateway, addUserGateway, hashGenerator);
	}

	@Bean
	BCryptAdapter bCryptAdapter(PasswordEncoder encoder) {
		return new BCryptAdapter(encoder);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

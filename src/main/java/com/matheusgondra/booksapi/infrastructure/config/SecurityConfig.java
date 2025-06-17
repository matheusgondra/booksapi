package com.matheusgondra.booksapi.infrastructure.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.matheusgondra.booksapi.infrastructure.enums.PublicRoutes;

@Configuration
public class SecurityConfig {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		String[] swaggerPaths = { "/docs/**", "/v3/api-docs/**", "/swagger-ui/**" };
		
		String[] publicPaths = Arrays.stream(PublicRoutes.values())
				.map(PublicRoutes::getRoute)
				.toArray(String[]::new);

		http
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers(publicPaths).permitAll();
					auth.requestMatchers(swaggerPaths).permitAll();
					auth.anyRequest().authenticated();
				});

		return http.build();
	}
}

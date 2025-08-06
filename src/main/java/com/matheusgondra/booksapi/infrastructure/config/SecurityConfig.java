package com.matheusgondra.booksapi.infrastructure.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.matheusgondra.booksapi.infrastructure.enums.PublicRoutes;
import com.matheusgondra.booksapi.infrastructure.security.SecurityFilter;

@Configuration
public class SecurityConfig {
	private final SecurityFilter securityFilter;

	public SecurityConfig(SecurityFilter securityFilter) {
		this.securityFilter = securityFilter;
	}

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
				})
				.httpBasic(Customizer.withDefaults())
				.addFilterBefore(this.securityFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}

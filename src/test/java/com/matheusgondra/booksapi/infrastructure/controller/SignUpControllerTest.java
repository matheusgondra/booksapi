package com.matheusgondra.booksapi.infrastructure.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusgondra.booksapi.infrastructure.dto.SignUpRequestDTO;
import com.matheusgondra.booksapi.infrastructure.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
public class SignUpControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository repository;

	private final SignUpRequestDTO dto = new SignUpRequestDTO("john", "doe", "johndoe@email.com", "Password@123");
	private final SignUpRequestDTO invalidDto = new SignUpRequestDTO("john", "doe", "johndoeemail.com", "Password@123");

	@BeforeEach
	void setUp() {
		this.repository.deleteAll();
	}

	@Test
	@DisplayName("Should return 201 on success")
	void case01() throws Exception {
		mockMvc.perform(this.signUp(this.dto)).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Should return 400 if request is invalid")
	void case02() throws Exception {
		mockMvc.perform(this.signUp(this.invalidDto)).andExpect(status().isBadRequest());
	}

	private RequestBuilder signUp(SignUpRequestDTO dto) throws Exception {
		return post("/api/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto));
	}
}

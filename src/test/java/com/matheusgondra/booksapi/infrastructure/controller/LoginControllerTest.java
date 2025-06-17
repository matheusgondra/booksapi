package com.matheusgondra.booksapi.infrastructure.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
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
import com.matheusgondra.booksapi.infrastructure.dto.LoginRequestDTO;
import com.matheusgondra.booksapi.infrastructure.dto.SignUpRequestDTO;
import com.matheusgondra.booksapi.infrastructure.repository.UserRepository;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository repository;

    private final LoginRequestDTO dto = new LoginRequestDTO("johndoe@email.com", "Password@123");
    private final LoginRequestDTO invalidDto = new LoginRequestDTO("johndoe@email.com", "WrongPassword@123");

    @BeforeEach
    void setUp() throws Exception {
        mockMvc.perform(this.signUp()).andExpect(status().isCreated());
    }

    @AfterEach
    void tearDown() {
        this.repository.deleteAll();
    }

    @Test
    @DisplayName("Should return 200 on success")
    void case01() throws Exception {
        mockMvc.perform(this.login(this.dto))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.access_token").exists());
    }

    @Test
    @DisplayName("Should return 401 if invalid credentials is provided")
    void case02() throws Exception {
        mockMvc.perform(this.login(this.invalidDto))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.error").value("Invalid credentials"));
    }

    private RequestBuilder signUp() throws Exception {
        SignUpRequestDTO signUpDTO = new SignUpRequestDTO("john", "doe", "johndoe@email.com", "Password@123");

        return post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDTO));
    }

    private RequestBuilder login(LoginRequestDTO dto) throws Exception {
        return post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto));
    }
}

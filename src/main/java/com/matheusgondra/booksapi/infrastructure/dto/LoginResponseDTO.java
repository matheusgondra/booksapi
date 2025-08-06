package com.matheusgondra.booksapi.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDTO(@JsonProperty("access_token") String accessToken) {}

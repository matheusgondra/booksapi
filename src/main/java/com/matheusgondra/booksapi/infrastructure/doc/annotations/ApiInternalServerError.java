package com.matheusgondra.booksapi.infrastructure.doc.annotations;

import com.matheusgondra.booksapi.infrastructure.error.ResponseError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApiResponse(
    responseCode = "500",
    description = "Internal server error",
    content =
        @Content(
            mediaType = "application/json",
            schema =
                @Schema(
                    implementation = ResponseError.class,
                    example = "{ \"status_code\": 500, \"error\": \"Internal Server Error\" }")))
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiInternalServerError {}

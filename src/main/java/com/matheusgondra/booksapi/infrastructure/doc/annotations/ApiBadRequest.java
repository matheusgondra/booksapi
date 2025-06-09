package com.matheusgondra.booksapi.infrastructure.doc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.matheusgondra.booksapi.infrastructure.error.ResponseError;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@ApiResponse(
    responseCode = "400", 
    description = "Invalid request data", 
    content = @Content(
        mediaType = "application/json", 
        schema = @Schema(
            implementation = ResponseError.class,
            example = "{ \"status_code\": 400, \"error\": \"Invalid email format\" }"
        )
    )
)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiBadRequest {
}

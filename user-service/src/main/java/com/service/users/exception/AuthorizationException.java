package com.service.users.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class AuthorizationException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public AuthorizationException(String message) {
        this.message = message;
        this.status = HttpStatus.UNAUTHORIZED;
    }
}

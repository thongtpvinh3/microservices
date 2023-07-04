package com.service.users.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseObject {
    private String message;
    private HttpStatus status;
    private Object result;
    public ResponseObject() {
    }

    public ResponseObject(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public ResponseObject(String message, HttpStatus status, Object result) {
        this.message = message;
        this.status = status;
        this.result = result;
    }

}

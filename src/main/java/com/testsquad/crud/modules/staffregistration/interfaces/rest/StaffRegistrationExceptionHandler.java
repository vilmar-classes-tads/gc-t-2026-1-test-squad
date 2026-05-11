package com.testsquad.crud.modules.staffregistration.interfaces.rest;

import com.testsquad.crud.modules.staffregistration.application.exception.CpfAlreadyRegisteredException;
import com.testsquad.crud.modules.staffregistration.application.exception.EmailAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StaffRegistrationExceptionHandler {

    @ExceptionHandler({CpfAlreadyRegisteredException.class, EmailAlreadyRegisteredException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiErrorResponse> handleBadRequest(RuntimeException exception) {
        return ResponseEntity.badRequest()
                .body(ApiErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "Bad Request", exception.getMessage()));
    }
}

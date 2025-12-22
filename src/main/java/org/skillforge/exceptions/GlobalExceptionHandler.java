package org.skillforge.exceptions;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.skillforge.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleValidation(InvalidInputException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(UserNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "something went wrong", request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus httpStatus, String message, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse.Builder()
                .timestamp(Instant.now())
                .status(httpStatus.value())
                .error(httpStatus.name())
                .message(message)
                .path(request.getRequestURI())
                .traceId(UUID.randomUUID().toString())
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }
}

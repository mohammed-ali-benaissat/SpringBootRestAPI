package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.DTO.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.UUID;

/**
 * 🔥 GlobalExceptionHandler = Centralized error handling for a REST API
 *
 * Teaching points:
 * 1️⃣ @ControllerAdvice makes this class "global":
 *      - Any exception thrown in a @RestController can be handled here.
 * 2️⃣ @ExceptionHandler maps a specific exception class to a handler method.
 * 3️⃣ Goal: return a consistent JSON structure (ErrorResponse) and HTTP status.
 * 4️⃣ Also log exceptions for debugging without exposing stack traces to clients.
 */
@ControllerAdvice
@Slf4j  // Enables logging with log.info, log.error, etc.
public class GlobalExceptionHandler {

    // ============================================================
    // 1️⃣ HANDLE VALIDATION ERRORS
    // ============================================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    /**
     * This method catches validation errors when Spring validates DTOs
     * (@NotBlank, @Email, @Min, @Max, @NotNull annotations in UserDto, etc.)
     */
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Collect all field error messages from the validation exception
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .toList();

        // Combine multiple error messages into a single string
        String message = String.join("; ", errors);

        // Return HTTP 400 Bad Request with structured error JSON
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message),
                HttpStatus.BAD_REQUEST
        );
    }

    // ============================================================
    // 2️⃣ HANDLE BUSINESS LOGIC EXCEPTIONS
    // ============================================================

    @ExceptionHandler(UserAlreadyExistsException.class)
    /**
     * Handles domain-specific exception when creating a user that already exists
     * Maps to HTTP 409 Conflict
     */
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    /**
     * Handles "resource not found" exception
     * Maps to HTTP 404 Not Found
     */
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotValidException.class)
    /**
     * Handles custom business validation failure
     * Maps to HTTP 400 Bad Request
     */
    public ResponseEntity<ErrorResponse> handleUserNotValid(UserNotValidException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Similarly handle related entity not found exceptions
    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProfileNotFound(ProfileNotFoundException ex){
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(OrderNotFoundException ex){
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFound(CategoryNotFoundException ex){
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex){
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    // ============================================================
    // 3️⃣ HANDLE STANDARD JAVA / DATABASE EXCEPTIONS
    // ============================================================

    @ExceptionHandler(IllegalArgumentException.class)
    /**
     * Handles invalid arguments passed to methods
     * Maps to HTTP 400 Bad Request
     */
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex){
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    /**
     * Handles database constraint violations (e.g., unique constraint)
     * Maps to HTTP 409 Conflict
     */
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex){
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT.value(), "Data integrity violation"),
                HttpStatus.CONFLICT);
    }

    // ============================================================
    // 4️⃣ FALLBACK / GENERAL EXCEPTION HANDLER
    // ============================================================

    @ExceptionHandler(Exception.class)
    /**
     * Handles all unanticipated exceptions
     * - Logs the exception with a unique reference ID
     * - Returns a safe HTTP 500 Internal Server Error message
     */
    public ResponseEntity<ErrorResponse> handleGeneralError(Exception ex){
        // Generate unique ID for debugging
        String errorId = UUID.randomUUID().toString();

        // Log full stack trace + reference ID for developers
        log.error("Unhandled exception id={}", errorId, ex);

        // Return safe error message to client
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected error occurred. Reference: " + errorId);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
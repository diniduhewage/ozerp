package com.onenzero.ozerp.appbase.error;

import com.onenzero.ozerp.appbase.dto.ApiResponseDto;
import com.onenzero.ozerp.appbase.error.exception.AccessDeniedException;
import com.onenzero.ozerp.appbase.error.exception.AuthorizationException;
import com.onenzero.ozerp.appbase.error.exception.BadRequestException;
import com.onenzero.ozerp.appbase.error.exception.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handle(BadRequestException e) {
        ApiError apiError = new ApiError(BAD_REQUEST, e.getMessage());
        return buildResponseEntity(apiError);
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handle(NotFoundException e) {

        ApiError apiError = new ApiError(NOT_FOUND, e.getMessage());
        return buildResponseEntity(apiError);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handle(MethodArgumentNotValidException e) {
        ApiError apiError = new ApiError(BAD_REQUEST);

        String message = Stream.concat(e.getBindingResult().getFieldErrors().stream(), e.getBindingResult().getGlobalErrors().stream())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst().orElse("Validation error");

        apiError.setMessage(message);
        apiError.addValidationErrors(e.getBindingResult().getFieldErrors());
        return buildResponseEntity(apiError);
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handle(BindException e) {
        ApiError apiError = new ApiError(BAD_REQUEST, e.getFieldErrors().get(0).getDefaultMessage());
        return buildResponseEntity(apiError);
    }

    @ResponseBody
    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ResponseEntity<Object> handle(NoSuchAlgorithmException e) {
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, e.getMessage());
        return buildResponseEntity(apiError);
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Object> handle(AuthorizationException e) {
        ApiError apiError = new ApiError(UNAUTHORIZED, e.getMessage());
        return buildResponseEntity(apiError);
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handle(AccessDeniedException e) {
        ApiError apiError = new ApiError(FORBIDDEN, e.getMessage());
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<Object> handleConstraintViolation(Exception e, WebRequest request) {
        Throwable cause = ((TransactionSystemException) e).getRootCause();
        if (cause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
            // do something here
            Iterator<ConstraintViolation<?>> iter = constraintViolations.iterator();
            ConstraintViolation<?> first = iter.next();
            ApiError apiError = new ApiError(BAD_REQUEST, first.getPropertyPath() + " " + first.getMessage());
            return buildResponseEntity(apiError);
        } else {
            ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, e.getMessage());
            return buildResponseEntity(apiError);
        }
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        ApiResponseDto data = new ApiResponseDto(false, apiError);
        return ResponseEntity.status(apiError.getStatus()).body(data);
    }
}

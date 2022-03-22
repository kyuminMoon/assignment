package com.kmmoon.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler({CustomException.class, ExecutionException.class})
    public ResponseEntity<?> handleCustomException(CustomException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ExceptionMessageDTO(e), e.getErrorStatus().getHttpStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException e) {
        e.printStackTrace();

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(ex -> errors.put(((FieldError) ex).getField(), ex.getDefaultMessage()));

        return getValidExceptionResponseEntity(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(ex -> errors.put(((FieldError) ex).getField(), ex.getDefaultMessage()));

        return getValidExceptionResponseEntity(errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse res, AccessDeniedException e) throws IOException {
        e.printStackTrace();
        res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
    }

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse res, Exception e) throws IOException {
        e.printStackTrace();
        res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
    }

    //Data Valid 관련 Response 처리
    private ResponseEntity<?> getValidExceptionResponseEntity(Map<String, String> errors) {
        ValidException<Map<String, String>> validException = new ValidException<>(ErrorStatus.INVALID_VALUE, errors);

        return new ResponseEntity<>(validException, validException.getErrorStatus().getHttpStatus());
    }

}

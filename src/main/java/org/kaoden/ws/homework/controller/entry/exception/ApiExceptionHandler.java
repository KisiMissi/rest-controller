package org.kaoden.ws.homework.controller.entry.exception;

import org.kaoden.ws.homework.exception.NotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody ExceptionDTO processNotFoundException(NotFoundException exception) {
        return ExceptionDTO.of(exception.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ExceptionDTO processNullArgumentException(MethodArgumentNotValidException exception) {
        return ExceptionDTO.of(Arrays.toString(exception.getDetailMessageArguments()));
    }
}

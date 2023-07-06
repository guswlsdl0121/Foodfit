package Foodfit.BackEnd.Controller.ExceptionHandler;

import Foodfit.BackEnd.DTO.Response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static Foodfit.BackEnd.Exception.BadRequestException.*;
import static Foodfit.BackEnd.Exception.NotFoundException.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EnglishSearchException.class)
    public ResponseEntity<ErrorResponse> handleEnglishSearchException(EnglishSearchException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoFoodException.class, NoUserException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, NoSuchElementException.class})
    public ResponseEntity<ErrorResponse> illegalValue(Exception e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}

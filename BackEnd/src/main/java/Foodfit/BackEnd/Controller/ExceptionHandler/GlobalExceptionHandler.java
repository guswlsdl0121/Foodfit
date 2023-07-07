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
    public ResponseEntity<ErrorResponse> handleEnglishSearchException(EnglishSearchException e) {
        return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoFoodException.class, NoUserException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(RuntimeException e) {
        return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {IllegalArgumentException.class, NoSuchElementException.class})
    public ResponseEntity<ErrorResponse> illegalValue(Exception e){
        return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> defaultException(Exception e){
        return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package Foodfit.BackEnd.Service.Controller.ExceptionHandler;


import Foodfit.BackEnd.DTO.Response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = {IllegalArgumentException.class, NoSuchElementException.class})
    public ResponseEntity<ErrorResponse> illegalValue(Exception e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}

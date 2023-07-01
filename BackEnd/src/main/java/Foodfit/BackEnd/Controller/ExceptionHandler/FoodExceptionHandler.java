package Foodfit.BackEnd.Controller.ExceptionHandler;

import Foodfit.BackEnd.Exception.FoodException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static Foodfit.BackEnd.Exception.FoodException.*;

@ControllerAdvice
public class FoodExceptionHandler {

    @ExceptionHandler(EnglishSearchException.class)
    public ResponseEntity<String> handleEnglishSearchException(EnglishSearchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NoSearchResultException.class)
    public ResponseEntity<String> handleNoSearchResultException(NoSearchResultException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}

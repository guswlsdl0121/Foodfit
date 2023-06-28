package Foodfit.BackEnd.Controller;


import Foodfit.BackEnd.Exception.NullFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(value={NullFieldException.class})
    private ResponseEntity<String> AdditionalFieldNull(Exception e){

        return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
    }

}

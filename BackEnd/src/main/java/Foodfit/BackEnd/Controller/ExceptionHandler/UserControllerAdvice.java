package Foodfit.BackEnd.Controller.ExceptionHandler;


import Foodfit.BackEnd.DTO.Response.ErrorResponse;
import Foodfit.BackEnd.Exception.NullFieldException;
import Foodfit.BackEnd.Exception.UnAuthorizedException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(value={NullFieldException.class})
    public ResponseEntity<ErrorResponse> AdditionalFieldNull(Exception e){
        return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value={UnAuthorizedException.class})
    public ResponseEntity<ErrorResponse> NoUserInContext(Exception e){
        return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.UNAUTHORIZED);
    }
}

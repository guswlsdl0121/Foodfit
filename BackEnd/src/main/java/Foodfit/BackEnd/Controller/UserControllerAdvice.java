package Foodfit.BackEnd.Controller;


import Foodfit.BackEnd.Exception.NullFieldException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(value={NullFieldException.class})
    public ResponseEntity<ErrorResponse> AdditionalFieldNull(Exception e){

        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value={NoSuchElementException.class})
    public ResponseEntity<ErrorResponse> NoUserInContext(Exception e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }


    @Getter
    class ErrorResponse{
        private LocalDate timeStamp;
        private String message;


        public ErrorResponse(String message) {
            timeStamp = LocalDate.now();
            this.message = message;
        }
    }
}

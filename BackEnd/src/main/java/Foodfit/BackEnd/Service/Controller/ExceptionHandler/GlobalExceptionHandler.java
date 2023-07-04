package Foodfit.BackEnd.Service.Controller.ExceptionHandler;

import Foodfit.BackEnd.DTO.Response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

import static Foodfit.BackEnd.Exception.BadRequestException.*;
import static Foodfit.BackEnd.Exception.NotFoundException.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EnglishSearchException.class)
    public ResponseEntity<ErrorResponse> handleEnglishSearchException(EnglishSearchException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({NoFoodException.class, NoUserException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    //TODO [HJ] 이거 수정해주세요
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("인증된 요청이 아닙니다!");
    }
}

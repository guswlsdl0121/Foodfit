package Foodfit.BackEnd.DTO.Response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ErrorResponse{
    private LocalDate timeStamp;
    private String message;


    public ErrorResponse(String message) {
        timeStamp = LocalDate.now();
        this.message = message;
    }
}

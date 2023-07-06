package Foodfit.BackEnd.DTO.Response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;

@Getter
public class ErrorResponse{
    private LocalDate timeStamp;
    private String message;
    private String detail;

    public ErrorResponse(Exception e) {
        timeStamp = LocalDate.now();
        this.message = e.getMessage();
        this.message = Arrays.toString(e.getCause().getStackTrace());
    }
}

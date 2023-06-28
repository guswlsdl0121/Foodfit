package Foodfit.BackEnd.Exception;

public class NullFieldException extends RuntimeException{

    public NullFieldException() {
    }

    public NullFieldException(String message) {
        super(message);
    }
}

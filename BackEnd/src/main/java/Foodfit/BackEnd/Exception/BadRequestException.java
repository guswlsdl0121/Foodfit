package Foodfit.BackEnd.Exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public static class EnglishSearchException extends BadRequestException {
        public EnglishSearchException() {
            super("검색은 한글로 하셔야 합니다.");
        }
    }

}


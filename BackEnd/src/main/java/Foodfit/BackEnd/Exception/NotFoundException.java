package Foodfit.BackEnd.Exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static class NoFoodException extends NotFoundException {
        public NoFoodException() {
            super("음식을 찾을 수 없습니다.");
        }
    }

    public static class NoUserException extends NotFoundException {
        public NoUserException() {
            super("사용자를 찾을 수 없습니다.");
        }
    }
}

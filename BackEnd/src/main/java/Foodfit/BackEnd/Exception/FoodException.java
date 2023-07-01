package Foodfit.BackEnd.Exception;

public class FoodException extends RuntimeException {
    public FoodException(String message) {
        super(message);
    }

    public static class NoSearchResultException extends FoodException {
        public NoSearchResultException(String message) {
            super(message);
        }
    }

    public static class EnglishSearchException extends FoodException {
        public EnglishSearchException(String message) {
            super(message);
        }
    }
}


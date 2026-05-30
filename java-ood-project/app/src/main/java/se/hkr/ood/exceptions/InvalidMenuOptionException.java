package se.hkr.ood.exceptions;

public class InvalidMenuOptionException extends RuntimeException {
    public InvalidMenuOptionException(String message) {
        super(message);
    }
}

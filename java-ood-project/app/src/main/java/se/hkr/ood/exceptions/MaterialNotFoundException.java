package se.hkr.ood.exceptions;

public class MaterialNotFoundException extends RuntimeException {
    public MaterialNotFoundException(String message) {
        super(message);
    }
}
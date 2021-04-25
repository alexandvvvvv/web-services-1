package com.company.exceptions;

public class CoffeeNotUniqueException extends Exception {
    private static final long serialVersionUID = -6647544772763931047L;
    public static CoffeeNotUniqueException DEFAULT_INSTANCE = new
            CoffeeNotUniqueException("coffee values should be unique");
    public CoffeeNotUniqueException(String message) {
        super(message);
    }
}

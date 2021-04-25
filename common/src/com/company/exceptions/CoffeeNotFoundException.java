package com.company.exceptions;

public class CoffeeNotFoundException extends Exception {
    private static final long serialVersionUID = -3347544772763931047L;
    public static CoffeeNotFoundException DEFAULT_INSTANCE = new
            CoffeeNotFoundException("coffee not found");
    public CoffeeNotFoundException(String message) {
        super(message);
    }
}

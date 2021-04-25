package com.company.exceptions;

public class CoffeeMissingPropertyException extends Exception {
    private static final long serialVersionUID = -3647544772763931047L;
    public static CoffeeNotFoundException DEFAULT_INSTANCE = new
            CoffeeNotFoundException("all properties must be specified");

    public CoffeeMissingPropertyException(String message) {
        super(message);
    }
}

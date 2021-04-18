package com.company.faults;

public class CoffeeNotUniqueFault {
    private static final String DEFAULT_MESSAGE = "Coffee must be unique";

    protected String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public static CoffeeNotUniqueFault defaultInstance() {
        CoffeeNotUniqueFault fault = new CoffeeNotUniqueFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}

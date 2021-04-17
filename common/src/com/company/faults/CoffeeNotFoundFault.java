package com.company.faults;

public class CoffeeNotFoundFault {
    private static final String DEFAULT_MESSAGE = "Coffee with specified ID not found";

    protected String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public static CoffeeNotFoundFault defaultInstance() {
        CoffeeNotFoundFault fault = new CoffeeNotFoundFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}

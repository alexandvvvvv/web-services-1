package com.company.faults;

public class CoffeeMissingPropertyFault {
    private static final String DEFAULT_MESSAGE = "All properties must be specified";

    protected String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public static CoffeeMissingPropertyFault defaultInstance() {
        CoffeeMissingPropertyFault fault = new CoffeeMissingPropertyFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}

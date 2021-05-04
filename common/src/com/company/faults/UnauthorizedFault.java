package com.company.faults;

public class UnauthorizedFault {
    private static final String DEFAULT_MESSAGE = "Invalid username or password";

    protected String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public static UnauthorizedFault defaultInstance() {
        UnauthorizedFault fault = new UnauthorizedFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}

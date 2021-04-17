package com.company.faults;

public class CoffeeSortIllegalFault {
    private static final String DEFAULT_MESSAGE = "Illegal coffee sort value. Allowed are ARABIC or ROBUST";

    protected String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public static CoffeeSortIllegalFault defaultInstance() {
        CoffeeSortIllegalFault fault = new CoffeeSortIllegalFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}

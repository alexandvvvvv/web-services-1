package com.company.exceptions;

import com.company.faults.CoffeeMissingPropertyFault;
import com.company.faults.CoffeeNotFoundFault;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.company.faults.CoffeeMissingPropertyFault")
public class CoffeeMissingPropertyException extends Exception {

    private final CoffeeMissingPropertyFault fault;
    public CoffeeMissingPropertyException(String message, CoffeeMissingPropertyFault fault) {
        super(message);
        this.fault = fault;
    }
    public CoffeeMissingPropertyException(String message, CoffeeMissingPropertyFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }
    public CoffeeMissingPropertyFault getFaultInfo() {
        return fault;
    }
}

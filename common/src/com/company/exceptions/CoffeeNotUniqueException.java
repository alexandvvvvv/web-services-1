package com.company.exceptions;

import com.company.faults.CoffeeMissingPropertyFault;
import com.company.faults.CoffeeNotUniqueFault;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.company.faults.CoffeeMissingPropertyFault")
public class CoffeeNotUniqueException extends Exception {

    private final CoffeeNotUniqueFault fault;
    public CoffeeNotUniqueException(String message, CoffeeNotUniqueFault fault) {
        super(message);
        this.fault = fault;
    }
    public CoffeeNotUniqueException(String message, CoffeeNotUniqueFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }
    public CoffeeNotUniqueFault getFaultInfo() {
        return fault;
    }
}

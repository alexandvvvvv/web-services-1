package com.company.exceptions;

import com.company.faults.CoffeeNotFoundFault;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.company.faults.CoffeeNotFoundFault")
public class CoffeeNotFoundException extends Exception {

    private final CoffeeNotFoundFault fault;
    public CoffeeNotFoundException(String message, CoffeeNotFoundFault fault) {
        super(message);
        this.fault = fault;
    }
    public CoffeeNotFoundException(String message, CoffeeNotFoundFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }
    public CoffeeNotFoundFault getFaultInfo() {
        return fault;
    }
}

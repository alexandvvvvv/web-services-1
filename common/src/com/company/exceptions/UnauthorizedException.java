package com.company.exceptions;

import com.company.faults.CoffeeNotFoundFault;
import com.company.faults.UnauthorizedFault;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.company.faults.UnauthorizedFault")
public class UnauthorizedException extends Exception {

    private final UnauthorizedFault fault;
    public UnauthorizedException(String message, UnauthorizedFault fault) {
        super(message);
        this.fault = fault;
    }
    public UnauthorizedException(String message, UnauthorizedFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }
    public UnauthorizedFault getFaultInfo() {
        return fault;
    }
}

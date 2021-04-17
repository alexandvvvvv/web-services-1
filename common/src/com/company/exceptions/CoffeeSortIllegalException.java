package com.company.exceptions;

import com.company.faults.CoffeeNotFoundFault;
import com.company.faults.CoffeeSortIllegalFault;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.company.faults.CoffeeSortIllegalFault")
public class CoffeeSortIllegalException extends Exception {

    private final CoffeeSortIllegalFault fault;
    public CoffeeSortIllegalException(String message, CoffeeSortIllegalFault fault) {
        super(message);
        this.fault = fault;
    }
    public CoffeeSortIllegalException(String message, CoffeeSortIllegalFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }
    public CoffeeSortIllegalFault getFaultInfo() {
        return fault;
    }
}

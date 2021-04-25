package com.company.exceptions;

public class CoffeeSortIllegalException extends Exception {

    private static final long serialVersionUID = -6647544772732631047L;
    public static CoffeeSortIllegalException DEFAULT_INSTANCE = new
            CoffeeSortIllegalException("coffe sort can be ARABIC or ROBUST");

    public CoffeeSortIllegalException(String message) {
        super(message);
    }
}

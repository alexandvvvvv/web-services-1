package com.company.exceptions;

import javax.ws.rs.WebApplicationException;

public class ThrottleException extends WebApplicationException {
    private static final long serialVersionUID = -2147544777463931047L;
    public static ThrottleException DEFAULT_INSTANCE = new
            ThrottleException("maximum requests is reached");
    public ThrottleException(String message) {
        super(new Exception(message));
    }
}

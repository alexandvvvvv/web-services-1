package com.company.exceptions;

public class UnauthorizedException extends Exception {
    private static final long serialVersionUID = -3347544777463931047L;
    public static UnauthorizedException DEFAULT_INSTANCE = new
            UnauthorizedException("invalid username or password");
    public UnauthorizedException(String message) {
        super(message);
    }
}

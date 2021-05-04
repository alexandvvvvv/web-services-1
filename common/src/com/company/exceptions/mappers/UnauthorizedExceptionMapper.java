package com.company.exceptions.mappers;

import com.company.exceptions.CoffeeNotFoundException;
import com.company.exceptions.UnauthorizedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse(UnauthorizedException e) {
        return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
package com.company.exceptions.mappers;

import com.company.exceptions.CoffeeNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CoffeeNotFoundExceptionMapper implements ExceptionMapper<CoffeeNotFoundException> {
    @Override
    public Response toResponse(CoffeeNotFoundException e) {
        return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
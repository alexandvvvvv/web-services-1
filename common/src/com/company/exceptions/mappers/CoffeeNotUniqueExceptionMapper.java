package com.company.exceptions.mappers;

import com.company.exceptions.CoffeeNotUniqueException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CoffeeNotUniqueExceptionMapper implements ExceptionMapper<CoffeeNotUniqueException> {
    @Override
    public Response toResponse(CoffeeNotUniqueException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
package com.company.exceptions.mappers;

import com.company.exceptions.CoffeeMissingPropertyException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CoffeeMissingPropertyExceptionMapper implements ExceptionMapper<CoffeeMissingPropertyException> {
    @Override
    public Response toResponse(CoffeeMissingPropertyException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
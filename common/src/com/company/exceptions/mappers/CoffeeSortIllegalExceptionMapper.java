package com.company.exceptions.mappers;

import com.company.exceptions.CoffeeSortIllegalException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CoffeeSortIllegalExceptionMapper implements ExceptionMapper<CoffeeSortIllegalException> {
    @Override
    public Response toResponse(CoffeeSortIllegalException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
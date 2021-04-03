package com.company;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService(serviceName = "CoffeeService")
public class CoffeeWebService {

    @WebMethod(operationName = "getCoffees")
    public List<Coffee> getCoffees() {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.getCoffees();
    }
    @WebMethod(operationName = "getFilteredCoffees")
    public List<Coffee> getFilteredCoffees(@WebParam(name="filter")CoffeeFilter filter) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.getFilteredCoffees(filter);
    }
    @WebMethod(operationName = "createCoffee")
    public long createCoffee(@WebParam(name="model")Coffee model) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.create(model);
    }
    @WebMethod(operationName = "updateCoffee")
    public boolean updateCoffee(@WebParam(name="id")int id, @WebParam(name="model")Coffee model) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.update(id, model);
    }
    @WebMethod(operationName = "deleteCoffee")
    public boolean deleteCoffee(@WebParam(name="id")int id) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.delete(id);
    }
}
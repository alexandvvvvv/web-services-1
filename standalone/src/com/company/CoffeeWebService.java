package com.company;

import com.company.exceptions.CoffeeNotFoundException;
import com.company.exceptions.CoffeeSortIllegalException;
import com.company.faults.CoffeeNotFoundFault;
import com.company.faults.CoffeeSortIllegalFault;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Arrays;
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
    public long createCoffee(@WebParam(name="model") CreateOrUpdateCoffeeRequest model)
            throws CoffeeSortIllegalException {
        checkSort(model.getSort());
        PostgreSQLDAO dao = new PostgreSQLDAO();

        return dao.create(fromModel(model));
    }
    @WebMethod(operationName = "updateCoffee")
    public boolean updateCoffee(@WebParam(name="id")int id, @WebParam(name="model") CreateOrUpdateCoffeeRequest model)
            throws CoffeeNotFoundException, CoffeeSortIllegalException  {
        checkSort(model.getSort());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        checkCoffeeExists(id, dao);
        return dao.update(id, model);
    }
    @WebMethod(operationName = "deleteCoffee")
    public boolean deleteCoffee(@WebParam(name="id")int id) throws CoffeeNotFoundException {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        checkCoffeeExists(id, dao);
        return dao.delete(id);
    }

    private void checkCoffeeExists(int id, PostgreSQLDAO dao) throws CoffeeNotFoundException {
        List<Coffee> allCoffees = dao.getCoffees();
        if (!allCoffees.stream().anyMatch(x -> x.getId() == id)) {
            CoffeeNotFoundFault fault = CoffeeNotFoundFault.defaultInstance();
            throw new CoffeeNotFoundException("coffee not found", fault);
        }
    }

    private void checkSort(String sort) throws CoffeeSortIllegalException {
        if (sort != null) {
            if (!Arrays.stream(CoffeeSort.values()).anyMatch(x -> x.name().equalsIgnoreCase(sort))) {
                CoffeeSortIllegalFault fault = CoffeeSortIllegalFault.defaultInstance();
                throw new CoffeeSortIllegalException("invalid sort value", fault);
            }
        }
    }

    private Coffee fromModel(CreateOrUpdateCoffeeRequest model) {
        return new Coffee(0, model.getName(), model.getCountry(), model.getCost(), CoffeeSort.valueOf(model.getSort()), model.getStrength());
    }
}
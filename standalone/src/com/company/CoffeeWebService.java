package com.company;

import com.company.exceptions.CoffeeMissingPropertyException;
import com.company.exceptions.CoffeeNotFoundException;
import com.company.exceptions.CoffeeNotUniqueException;
import com.company.exceptions.CoffeeSortIllegalException;
import com.company.faults.CoffeeMissingPropertyFault;
import com.company.faults.CoffeeNotFoundFault;
import com.company.faults.CoffeeNotUniqueFault;
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
    public List<Coffee> getFilteredCoffees(@WebParam(name="filter")CoffeeFilter filter)
            throws CoffeeSortIllegalException {
        checkSort(filter.getSort());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.getFilteredCoffees(filter);
    }
    @WebMethod(operationName = "createCoffee")
    public long createCoffee(@WebParam(name="model") CreateOrUpdateCoffeeRequest model)
            throws CoffeeSortIllegalException, CoffeeMissingPropertyException, CoffeeNotUniqueException {
        checkMissingProperties(model);
        checkSort(model.getSort());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        checkUniqueness(dao, model, null);
        return dao.create(fromModel(model));
    }
    @WebMethod(operationName = "updateCoffee")
    public boolean updateCoffee(@WebParam(name="id")int id, @WebParam(name="model") CreateOrUpdateCoffeeRequest model)
            throws CoffeeNotFoundException, CoffeeSortIllegalException, CoffeeNotUniqueException  {
        checkSort(model.getSort());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        checkCoffeeExists(id, dao);
        checkUniqueness(dao, model, id);
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

    private void checkUniqueness(PostgreSQLDAO dao, CreateOrUpdateCoffeeRequest model, Integer id) throws CoffeeNotUniqueException {
        List<Coffee> allCoffees = dao.getCoffees();
        if (allCoffees.stream().anyMatch(x -> (id == null || x.getId() != id)
            && x.getCost() == model.getCost()
            && x.getSort() == CoffeeSort.valueOf(model.getSort())
            && x.getName().equalsIgnoreCase(model.getName())
            && x.getCountry().equalsIgnoreCase(model.getCountry())
            && x.getStrength() == model.getStrength())) {
            CoffeeNotUniqueFault fault = CoffeeNotUniqueFault.defaultInstance();
            throw new CoffeeNotUniqueException("coffee with specified values already exists", fault);
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

    private void checkMissingProperties(CreateOrUpdateCoffeeRequest model) throws CoffeeMissingPropertyException {
        if (model.getName() == null || model.getCountry() == null ||
                model.getSort() == null || model.getStrength() == null || model.getCost() == null) {
            CoffeeMissingPropertyFault fault = CoffeeMissingPropertyFault.defaultInstance();
            throw new CoffeeMissingPropertyException("all properties must be specified", fault);
        }
    }
    private Coffee fromModel(CreateOrUpdateCoffeeRequest model) {
        return new Coffee(0, model.getName(), model.getCountry(), model.getCost(), CoffeeSort.valueOf(model.getSort()), model.getStrength());
    }
}
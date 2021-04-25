package com.company;

import com.company.exceptions.CoffeeMissingPropertyException;
import com.company.exceptions.CoffeeNotFoundException;
import com.company.exceptions.CoffeeNotUniqueException;
import com.company.exceptions.CoffeeSortIllegalException;
import com.company.faults.CoffeeMissingPropertyFault;
import com.company.faults.CoffeeNotFoundFault;
import com.company.faults.CoffeeNotUniqueFault;
import com.company.faults.CoffeeSortIllegalFault;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/coffees")
public class CoffeeResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Coffee> getFilteredCoffees(@QueryParam("cost") Integer cost,
                                           @QueryParam("strength") Integer strength,
                                           @QueryParam("name") String name,
                                           @QueryParam("country") String country,
                                           @QueryParam("sort") String sort)
            throws CoffeeSortIllegalException {
        checkSort(sort);
        PostgreSQLDAO dao = new PostgreSQLDAO();
        CoffeeFilter filter = new CoffeeFilter(name, country, cost, sort, strength);
        return dao.getFilteredCoffees(filter);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String createCoffee(CreateOrUpdateCoffeeRequest model)
            throws CoffeeSortIllegalException, CoffeeMissingPropertyException, CoffeeNotUniqueException {
        checkMissingProperties(model);
        checkSort(model.getSort());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        checkUniqueness(dao, model, null);
        return String.valueOf(dao.create(fromModel(model)));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateCoffee(@QueryParam("id")int id, CreateOrUpdateCoffeeRequest model)
            throws CoffeeNotFoundException, CoffeeSortIllegalException, CoffeeNotUniqueException  {
        checkSort(model.getSort());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        checkCoffeeExists(id, dao);
        checkUniqueness(dao, model, id);
        return String.valueOf(dao.update(id, model));
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteCoffee(@QueryParam("id") int id) throws CoffeeNotFoundException {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        checkCoffeeExists(id, dao);
        return String.valueOf(dao.delete(id));
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
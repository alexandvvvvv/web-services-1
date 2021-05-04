package com.company;

import com.company.exceptions.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.*;

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
    public String createCoffee(@HeaderParam("authorization") String authString, CreateOrUpdateCoffeeRequest model)
            throws CoffeeSortIllegalException, CoffeeMissingPropertyException, CoffeeNotUniqueException, UnauthorizedException {
        checkAuth(authString);
        checkMissingProperties(model);
        checkSort(model.getSort());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        checkUniqueness(dao, model, null);
        return String.valueOf(dao.create(fromModel(model)));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateCoffee(@HeaderParam("authorization") String authString, @QueryParam("id")int id, CreateOrUpdateCoffeeRequest model)
            throws CoffeeNotFoundException, CoffeeSortIllegalException, CoffeeNotUniqueException, UnauthorizedException {
        checkAuth(authString);
        checkSort(model.getSort());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        checkCoffeeExists(id, dao);
        checkUniqueness(dao, model, id);
        return String.valueOf(dao.update(id, model));
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteCoffee(@HeaderParam("authorization") String authString, @QueryParam("id") int id) throws CoffeeNotFoundException, UnauthorizedException {
        checkAuth(authString);
        PostgreSQLDAO dao = new PostgreSQLDAO();
        checkCoffeeExists(id, dao);
        return String.valueOf(dao.delete(id));
    }

    private void checkAuth(String authString) throws UnauthorizedException {
        if (authString == null)
            throw UnauthorizedException.DEFAULT_INSTANCE;
        final String encodedUserPassword = authString.replaceFirst("Basic"
                + " ", "");
        String usernameAndPassword = null;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(
                    encodedUserPassword);
            usernameAndPassword = new String(decodedBytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final StringTokenizer tokenizer = new StringTokenizer(
                usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        if (!"Username".equals(username) || !"P@$$W0RD".equals(password)) {
            throw UnauthorizedException.DEFAULT_INSTANCE;
        }
    }

    private void checkCoffeeExists(int id, PostgreSQLDAO dao) throws CoffeeNotFoundException {
        List<Coffee> allCoffees = dao.getCoffees();
        if (!allCoffees.stream().anyMatch(x -> x.getId() == id)) {
            throw new CoffeeNotFoundException("coffee not found");
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
            throw new CoffeeNotUniqueException("coffee with specified values already exists");
        }
    }


    private void checkSort(String sort) throws CoffeeSortIllegalException {
        if (sort != null) {
            if (!Arrays.stream(CoffeeSort.values()).anyMatch(x -> x.name().equalsIgnoreCase(sort))) {
                throw new CoffeeSortIllegalException("invalid sort value");
            }
        }
    }

    private void checkMissingProperties(CreateOrUpdateCoffeeRequest model) throws CoffeeMissingPropertyException {
        if (model.getName() == null || model.getCountry() == null ||
                model.getSort() == null || model.getStrength() == null || model.getCost() == null) {
            throw new CoffeeMissingPropertyException("all properties must be specified");
        }
    }
    private Coffee fromModel(CreateOrUpdateCoffeeRequest model) {
        return new Coffee(0, model.getName(), model.getCountry(), model.getCost(), CoffeeSort.valueOf(model.getSort()), model.getStrength());
    }
}
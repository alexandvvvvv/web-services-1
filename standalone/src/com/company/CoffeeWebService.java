package com.company;

import com.company.exceptions.*;
import com.company.faults.*;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebService(serviceName = "CoffeeService")
public class CoffeeWebService {

    @Resource
    WebServiceContext wsctx;

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
            throws CoffeeSortIllegalException, CoffeeMissingPropertyException, CoffeeNotUniqueException, UnauthorizedException {
        checkAuth();
        checkMissingProperties(model);
        checkSort(model.getSort());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        checkUniqueness(dao, model, null);
        return dao.create(fromModel(model));
    }
    @WebMethod(operationName = "updateCoffee")
    public boolean updateCoffee(@WebParam(name="id")int id, @WebParam(name="model") CreateOrUpdateCoffeeRequest model)
            throws CoffeeNotFoundException, CoffeeSortIllegalException, CoffeeNotUniqueException, UnauthorizedException {
        checkAuth();
        checkSort(model.getSort());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        checkCoffeeExists(id, dao);
        checkUniqueness(dao, model, id);
        return dao.update(id, model);
    }
    @WebMethod(operationName = "deleteCoffee")
    public boolean deleteCoffee(@WebParam(name="id")int id) throws CoffeeNotFoundException, UnauthorizedException {
        checkAuth();
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

    private void checkAuth() throws UnauthorizedException {
        MessageContext mctx = wsctx.getMessageContext();

        //get detail from request headers
        Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
        List userList = (List) http_headers.get("Username");
        List passList = (List) http_headers.get("Password");

        String username = "";
        String password = "";

        if(userList!=null){
            username = userList.get(0).toString();
        }

        if(passList!=null){
            password = passList.get(0).toString();
        }

        if (!username.equals("Username") || !password.equals("P@$$W0RD")){
            UnauthorizedFault fault = UnauthorizedFault.defaultInstance();
            throw new UnauthorizedException(fault.getMessage(), fault);
        }
    }

    private Coffee fromModel(CreateOrUpdateCoffeeRequest model) {
        return new Coffee(0, model.getName(), model.getCountry(), model.getCost(), CoffeeSort.valueOf(model.getSort()), model.getStrength(), model.getImage());
    }
}
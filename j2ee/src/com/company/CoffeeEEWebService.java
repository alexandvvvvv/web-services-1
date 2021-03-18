package com.company;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebService(serviceName = "CoffeeService")
public class CoffeeEEWebService {

    public CoffeeEEWebService() {}

    @Resource(lookup = "jdbc/ifmo-ws")
    private DataSource dataSource;

    @WebMethod(operationName = "getCoffees")
    public List<Coffee> getCoffees() {
        PostgreSQLEEDAO dao = new PostgreSQLEEDAO(getConnection());
        return dao.getCoffees();
    }
    @WebMethod(operationName = "getFilteredCoffees")
    public List<Coffee> getFilteredCoffees(@WebParam(name="filter") CoffeeFilter filter) {
        PostgreSQLEEDAO dao = new PostgreSQLEEDAO(getConnection());
        return dao.getFilteredCoffees(filter);
    }
    private Connection getConnection() {
        Connection result = null;
        try {
            result = dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(CoffeeEEWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
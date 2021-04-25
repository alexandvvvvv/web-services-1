package com.company;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
@Path("/coffees")
@Produces(MediaType.APPLICATION_JSON)
public class CoffeeEEWebService {

    public CoffeeEEWebService() {}

    @Resource(lookup = "jdbc/ifmo-ws")
    private DataSource dataSource;

    @GET
    public List<Coffee> getFilteredCoffees(@QueryParam("cost") Integer cost,
                                           @QueryParam("strength") Integer strength,
                                           @QueryParam("name") String name,
                                           @QueryParam("country") String country,
                                           @QueryParam("sort") String sort) {
        CoffeeFilter filter = new CoffeeFilter(name, country, cost, sort, strength);
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
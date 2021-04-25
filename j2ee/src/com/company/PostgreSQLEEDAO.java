package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQLEEDAO {
    private Connection connection;

    public PostgreSQLEEDAO(Connection connection){
        this.connection = connection;
    }

    public List<Coffee> getCoffees() {
        List<Coffee> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from coffee");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                int cost = rs.getInt("cost");
                int sort = rs.getInt("sort");
                int strength = rs.getInt("strength");
                String image = rs.getString("image");

                Coffee coffee = new Coffee(id, name, country, cost, CoffeeSort.values()[sort], strength, image);
                result.add(coffee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLEEDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }


    public List<Coffee> getFilteredCoffees(CoffeeFilter filter) {
        List<Coffee> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from coffee");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                int cost = rs.getInt("cost");
                CoffeeSort sort = CoffeeSort.values()[rs.getInt("sort")];
                int strength = rs.getInt("strength");
                String image = rs.getString("image");

                if (!isNullOrBlank(filter.getName())) {
                    if (!name.equalsIgnoreCase(filter.getName())) continue;
                }

                if (!isNullOrBlank(filter.getCountry())) {
                    if (!country.equalsIgnoreCase(filter.getCountry())) continue;
                }

                if (filter.getCost() != null) {
                    if (cost != filter.getCost()) continue;
                }

                if (filter.getSort() != null) {
                    if (sort != CoffeeSort.valueOf(filter.getSort())) continue;
                }

                if (filter.getStrength() != null) {
                    if (strength != filter.getStrength()) continue;
                }

                Coffee coffee = new Coffee(id, name, country, cost, sort, strength, image);
                result.add(coffee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLEEDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    private boolean isNullOrBlank(String string) {
        return string == null || string.isEmpty();
    }
}
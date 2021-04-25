package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class PostgreSQLDAO {

    public List<Coffee> getCoffees() {
        List<Coffee> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()){
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
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }


    public List<Coffee> getFilteredCoffees(CoffeeFilter filter) {
        List<Coffee> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()){
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
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public long create(Coffee model) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet result = stmt.executeQuery("INSERT INTO COFFEE (NAME, COUNTRY, COST, SORT, STRENGTH, IMAGE) VALUES " +
                    "('" + model.getName() + "', '" + model.getCountry() + "', '" + model.getCost() +
                    "', '" + model.getSort().ordinal() + "', '" + model.getStrength() + "', '" + model.getImage() + "') RETURNING id");

            if (result.next()) {
                return (result.getInt("id"));
            }
            throw new SQLException("Creating coffee failed, no ID obtained.");

        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }


    public boolean delete(int id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            int affectedRows = stmt.executeUpdate("DELETE FROM coffee WHERE id = " + id);

            return affectedRows == 1;

        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean update(int id, CreateOrUpdateCoffeeRequest model) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection. createStatement();
            String sql = "UPDATE COFFEE ";
            boolean anyUpdates = false;
            if (!isNullOrBlank(model.getName())) {
                anyUpdates = true;
                sql += "SET NAME = '" + model.getName() + "'";
            }

            if (!isNullOrBlank(model.getCountry())) {
                sql += (anyUpdates ? ", " : " SET ") + "COUNTRY = '" + model.getCountry() + "'";
                anyUpdates = true;
            }

            if (model.getCost() != null) {
                sql += (anyUpdates ? ", " : " SET ") + "COST = '" + model.getCost() + "'";
                anyUpdates = true;
            }

            if (model.getSort() != null) {
                int sort = CoffeeSort.valueOf(model.getSort()).ordinal();
                sql += (anyUpdates ? ", " : " SET ") + "SORT = '" + sort + "'";
                anyUpdates = true;
            }

            if (model.getStrength() != null) {
                sql += (anyUpdates ? ", " : " SET ") + "STRENGTH = '" + model.getStrength() + "'";
                anyUpdates = true;
            }

            if (anyUpdates) {
                int affectedRows = stmt.executeUpdate(sql +
                        " WHERE id = " + id);

                return affectedRows != 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean isNullOrBlank(String string) {
        return string == null || string.isEmpty();
    }
}
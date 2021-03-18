package com.company;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class WebServiceClient {

    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8082/CoffeeService?wsdl");
        CoffeeService personService = new CoffeeService(url);

        System.out.println("Enter values to get filtered coffees. To omit filtering on any value just press Enter. To finish program enter q when entering name is suggested");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            CoffeeFilter filter = new CoffeeFilter();
            String temp;

            System.out.println("Enter name:");
            temp = scanner.nextLine();
            if (temp.equalsIgnoreCase("q")) break;
            if (!temp.isEmpty()) {
                filter.setName(temp);
            }

            System.out.println("Enter country:");
            temp = scanner.nextLine();
            if (!temp.isEmpty()) {
                filter.setCountry(temp);
            }

            System.out.println("Enter sort (ARABIC or ROBUST):");
            temp = scanner.nextLine();
            if (!temp.isEmpty()) {
               filter.setSort(CoffeeSort.fromValue(temp));
            }

            System.out.println("Enter strength (1..10):");
            temp = scanner.nextLine();
            if (!temp.isEmpty()) {
                filter.setStrength(Integer.parseInt(temp));
            }

            System.out.println("Enter cost:");
            temp = scanner.nextLine();
            if (!temp.isEmpty()) {
                filter.setCost(Integer.parseInt(temp));
            }

            List<Coffee> coffees = personService.getCoffeeWebServicePort().getFilteredCoffees(filter);
            for (Coffee coffee : coffees) {
                System.out.println("name: " + coffee.getName() +
                        ", country: " + coffee.getCountry()+
                        ", sort: " + coffee.getSort()+
                        ", cost: " + coffee.getCost()+
                        ", strength: " + coffee.getStrength());
            }

            System.out.println("Total coffees: " + coffees.size());

        }
    }
}
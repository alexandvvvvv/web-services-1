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
            System.out.println("Enter 1 to get filtered output, 2 to create, 3 to update, 4 to delete or 0 to exit");
            String temp;
            temp = scanner.nextLine();

            Integer type = Integer.parseInt((temp));
            try {
                switch (type) {
                    case 0:
                        return;
                    case 1:
                        listFiltered(personService, scanner);
                        break;
                    case 2:
                        create(personService, scanner);
                        break;
                    case 3:
                        update(personService, scanner);
                        break;
                    case 4:
                        delete(personService, scanner);
                        break;
                    default:
                        System.out.println("ALlowed input is from 1 to 4");
                }
            }
            catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }

        }
    }

    private static void create(CoffeeService service, Scanner scanner) throws CoffeeMissingPropertyException, CoffeeNotUniqueException, CoffeeSortIllegalException{
        CreateOrUpdateCoffeeRequest model = getCoffee(scanner);
        long id = service.getCoffeeWebServicePort().createCoffee(model);
        System.out.println("Created coffee with ID: " + id);
    }

    private static void update(CoffeeService service, Scanner scanner) throws CoffeeNotFoundException, CoffeeNotUniqueException, CoffeeSortIllegalException {
        int id = getId(scanner);
        CreateOrUpdateCoffeeRequest model = getCoffee(scanner);
        boolean result = service.getCoffeeWebServicePort().updateCoffee(id, model);
        System.out.println(result ? "Updated " : "Failed to update " + "coffee with ID:  " + id);
    }

    private static void delete(CoffeeService service, Scanner scanner) throws CoffeeNotFoundException {
        int id = getId(scanner);
        boolean result = service.getCoffeeWebServicePort().deleteCoffee(id);
        System.out.println(result ? "Deleted " : "Failed to delete " + "coffee with ID:  " + id);
    }

    private static void listFiltered(CoffeeService service, Scanner scanner) throws CoffeeSortIllegalException {
        CoffeeFilter filter = new CoffeeFilter();
        String temp;

        System.out.println("Enter name:");
        temp = scanner.nextLine();
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
            filter.setSort(temp);
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

        List<Coffee> coffees = service.getCoffeeWebServicePort().getFilteredCoffees(filter);
        for (Coffee coffee : coffees) {
            System.out.println("name: " + coffee.getName() +
                    ", country: " + coffee.getCountry()+
                    ", sort: " + coffee.getSort()+
                    ", cost: " + coffee.getCost()+
                    ", strength: " + coffee.getStrength());
        }

        System.out.println("Total coffees: " + coffees.size());
    }

    private static int getId(Scanner scanner) {
        System.out.println("Enter ID:");
        while (true) {
            String temp = scanner.nextLine();
            if (!temp.isEmpty()) {
                return Integer.parseInt(temp);
            }
            System.out.println("ID must be an integer");
        }
    }

    private static CreateOrUpdateCoffeeRequest getCoffee(Scanner scanner) {
        CreateOrUpdateCoffeeRequest model = new CreateOrUpdateCoffeeRequest();
        String temp;

        System.out.println("Enter name:");
        temp = scanner.nextLine();
        if (!temp.isEmpty()) {
            model.setName(temp);
        }

        System.out.println("Enter country:");
        temp = scanner.nextLine();
        if (!temp.isEmpty()) {
            model.setCountry(temp);
        }

        System.out.println("Enter sort (ARABIC or ROBUST):");
        temp = scanner.nextLine();
        if (!temp.isEmpty()) {
            model.setSort(temp);
        }

        System.out.println("Enter strength (1..10):");
        temp = scanner.nextLine();
        if (!temp.isEmpty()) {
            model.setStrength(Integer.parseInt(temp));
        }

        System.out.println("Enter cost:");
        temp = scanner.nextLine();
        if (!temp.isEmpty()) {
            model.setCost(Integer.parseInt(temp));
        }
        return model;
    }
}
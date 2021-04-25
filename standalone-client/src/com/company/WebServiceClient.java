package com.company;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Scanner;

public class WebServiceClient {

    private static String URL = "http://localhost:8081/rest/coffees";
    public static void main(String[] args)  {

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
                        listFiltered(scanner);
                        break;
                    case 2:
                        create(scanner);
                        break;
                    case 3:
                        update(scanner);
                        break;
                    case 4:
                        delete(scanner);
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

    private static void create(Scanner scanner) {
        Client client = createClient();
        CreateOrUpdateCoffeeRequest model = getCoffee(scanner);

        WebResource webResource = client.resource(URL);

        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).post(ClientResponse.class, model);
        checkResponseStatus(response);

        GenericType<String> type = new GenericType<String>() {};
        String id = response.getEntity(type);
        System.out.println("Created coffee with ID: " + id);
    }

    private static void update(Scanner scanner) {
        Client client = createClient();
        int id = getId(scanner);
        CreateOrUpdateCoffeeRequest model = getCoffee(scanner);

        WebResource webResource = client.resource(URL);
        webResource = webResource.queryParam("id", String.valueOf(id));

        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).put(ClientResponse.class, model);
        checkResponseStatus(response);

        GenericType<String> type = new GenericType<String>() {};
        String result = response.getEntity(type);

        System.out.println(result.equalsIgnoreCase("true") ? "Updated " : "Failed to update " + "coffee with ID:  " + id);
    }

    private static void delete(Scanner scanner) {
        Client client = createClient();
        int id = getId(scanner);

        WebResource webResource = client.resource(URL);
        webResource = webResource.queryParam("id", String.valueOf(id));

        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).delete(ClientResponse.class);
        checkResponseStatus(response);

        GenericType<String> type = new GenericType<String>() {};
        String result = response.getEntity(type);

        System.out.println(result.equalsIgnoreCase("true") ? "Deleted " : "Failed to delete " + "coffee with ID:  " + id);
    }

    private static void listFiltered(Scanner scanner) {
        Client client = createClient();
        String temp;

        WebResource webResource = client.resource(URL);

        System.out.println("Enter name:");
        temp = scanner.nextLine();
        if (!temp.isEmpty()) {
            webResource = webResource.queryParam("name", temp);
        }

        System.out.println("Enter country:");
        temp = scanner.nextLine();
        if (!temp.isEmpty()) {
            webResource = webResource.queryParam("country", temp);
        }

        System.out.println("Enter sort (ARABIC or ROBUST):");
        temp = scanner.nextLine();
        if (!temp.isEmpty()) {
            webResource = webResource.queryParam("sort", temp);
        }

        System.out.println("Enter strength (1..10):");
        temp = scanner.nextLine();
        if (!temp.isEmpty()) {
            webResource = webResource.queryParam("strength", temp);
        }

        System.out.println("Enter cost:");
        temp = scanner.nextLine();
        if (!temp.isEmpty()) {
            webResource = webResource.queryParam("cost", temp);
        }

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        checkResponseStatus(response);

        GenericType<List<Coffee>> type = new GenericType<List<Coffee>>() {};
        List<Coffee> coffees = response.getEntity(type);

        for (Coffee coffee : coffees) {
            System.out.println("ID: " + coffee.getId() +
                    ", name: " + coffee.getName() +
                    ", country: " + coffee.getCountry()+
                    ", sort: " + coffee.getSort()+
                    ", cost: " + coffee.getCost()+
                    ", strength: " + coffee.getStrength());
        }

        System.out.println("Total coffees: " + coffees.size());
    }

    private static void checkResponseStatus(ClientResponse response) {
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            GenericType<String> type = new GenericType<String>() {};
            String reason = response.getEntity(type);
            throw new IllegalStateException("Request failed: " + reason);
        }
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

    private static Client createClient() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);  // <----- set the json configuration POJO MAPPING for JSON response paring
        return Client.create(clientConfig);
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
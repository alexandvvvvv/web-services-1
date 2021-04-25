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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class WebServiceClient {

    public static void main(String[] args) throws MalformedURLException {
        String url = "http://desktop-1m0cc2j:8080/lab1/rest/coffees";

        System.out.println("Enter values to get filtered coffees. To omit filtering on any value just press Enter. To finish program enter q when entering name is suggested");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Client client = createClient();
            String temp;

            WebResource webResource = client.resource(url);

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

            ClientResponse response =
                    webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                throw new IllegalStateException("Request failed");
            }

            GenericType<List<Coffee>> type = new GenericType<List<Coffee>>() {};
            List<Coffee> coffees = response.getEntity(type);

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
    private static Client createClient() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);  // <----- set the json configuration POJO MAPPING for JSON response paring
        return Client.create(clientConfig);
    }
}
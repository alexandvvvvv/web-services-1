package com.company;
import javax.xml.ws.Endpoint;

public class Main {

    public static void main(String[] args) {
        String url = "http://0.0.0.0:8082/CoffeeService";
        Endpoint.publish(url, new CoffeeWebService());
    }
}

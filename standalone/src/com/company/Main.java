package com.company;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final URI BASE_URI = URI.create("http://localhost:8081/rest/");
    public static void main(String[] args) {
        HttpServer server = null;
        try {
            ResourceConfig resourceConfig = new ClassNamesResourceConfig(CoffeeResource.class);
            final Map<String, Object> config = new HashMap<String, Object>();
            config.put("com.sun.jersey.api.json.POJOMappingFeature", true);
            resourceConfig.setPropertiesAndFeatures(config);
            server = GrizzlyServerFactory.createHttpServer(BASE_URI, resourceConfig);
            server.start();
            System.in.read();
            stopServer(server);
        } catch (IOException e) {
            e.printStackTrace();
            stopServer(server);
        }
    }

    private static void stopServer(HttpServer server) {
        if (server != null)
            server.stop();
    }
}

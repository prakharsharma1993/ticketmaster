package com.example.ticketmaster.dao;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Component
public class DataConsumer {
    @Autowired
    public ObjectMapper objectMapper;

    public <T> Flux<T> consumeJson(String apiUrl, Class<T[]> responseType) throws InterruptedException, IOException {
        // Create an instance of the HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create a GET request object
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String jsonData = response.body();

        T[] objects = objectMapper.readValue(jsonData, responseType);
        return Flux.fromArray(objects);
    }


}

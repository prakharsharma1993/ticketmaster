package com.example.ticketmaster;

import com.example.ticketmaster.dao.DataConsumer;
import com.example.ticketmaster.dto.VenueDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataConsumerTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DataConsumer dataConsumer;

    private final HttpClient clientMock = mock(HttpClient.class);

    private final HttpResponse<String> responseMock = mock(HttpResponse.class);


    @Test
    public void testConsumeJson_Success() throws IOException, InterruptedException {
        String apiUrl = "https://example.com/api/v1/users";
        Class<Person[]> responseType = Person[].class;
        Person[] expectedUsers = new Person[]{new Person(1,"John Doe", 21), new Person(2,"Jane Doe", 22)};

        when(clientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(responseMock);
        Mockito.doReturn(expectedUsers).when(objectMapper).readValue(Mockito.anyString(), Mockito.eq(responseType));
        Flux<Person> actualUsers = dataConsumer.consumeJson(apiUrl, responseType);
        assertEquals(Arrays.asList(expectedUsers), actualUsers.collectList().block());
    }
    }





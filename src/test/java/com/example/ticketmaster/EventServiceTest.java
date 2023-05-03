package com.example.ticketmaster;

import com.example.ticketmaster.dao.DataConsumer;
import com.example.ticketmaster.dto.ArtistDTO;
import com.example.ticketmaster.dto.EventDTO;
import com.example.ticketmaster.dto.VenueDTO;
import com.example.ticketmaster.exception.ArtistNotFoundException;
import com.example.ticketmaster.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private DataConsumer dataConsumer;



    @Test
    void testProcessArtistInfoInFlux_ValidArtistId_ReturnsMonoArtistDTO() throws IOException, InterruptedException {
        String artistId = "123";
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setId(artistId);
        artistDTO.setName("Prakhar");
        List<ArtistDTO> artistDTOList = new ArrayList<>();
        artistDTOList.add(artistDTO);
        Flux<ArtistDTO> artistDTOSFlux = Flux.fromIterable(artistDTOList);
        List<VenueDTO> venueDTOList = new ArrayList<>();
        VenueDTO venueDTO = new VenueDTO();
        venueDTO.setId("456");
        venueDTO.setName("Test Venue");
        venueDTO.setCity("Test City");
        venueDTO.setUrl("https://testvenue.com");
        venueDTOList.add(venueDTO);
        List<EventDTO> eventDTOList = new ArrayList<>();
        EventDTO eventDTO = new EventDTO();
        eventDTO.setTitle("Test Event");
        eventDTO.setStartDate("2023-05-05T18:00:00Z");
        eventDTO.setArtists(artistDTOList);
        eventDTO.setVenue(venueDTO);
        eventDTOList.add(eventDTO);
        Flux<EventDTO> eventDTOsFlux = Flux.fromIterable(eventDTOList);

        Flux<VenueDTO> venueDTOSFlux = Flux.fromIterable(venueDTOList);

        Mockito.when(dataConsumer.consumeJson(any(), eq(ArtistDTO[].class))).thenReturn(artistDTOSFlux);
        Mockito.when(dataConsumer.consumeJson(any(), eq(EventDTO[].class))).thenReturn(eventDTOsFlux);
        Mockito.when(dataConsumer.consumeJson(any(), eq(VenueDTO[].class))).thenReturn(venueDTOSFlux);

        Mono<ArtistDTO> result = eventService.processArtistInfoInFlux(artistId);

        StepVerifier.create(result)
                .expectNextMatches(a -> a.getId().equals(artistId) && a.getEvents().size() == 1
                        && a.getEvents().get(0).getTitle().equals("Test Event")
                        && a.getEvents().get(0).getVenueName().equals("Test Venue")
                        && a.getEvents().get(0).getVenueUrl().equals("https://testvenue.com")
                        && a.getEvents().get(0).getCity().equals("Test City"))
                .verifyComplete();
    }

    @Test
    void testProcessArtistInfoInFlux_InvalidArtistId_ThrowsArtistNotFoundException() throws IOException, InterruptedException {
        String artistId = "123";
        List<ArtistDTO> artistDTOList = new ArrayList<>();
        Flux<ArtistDTO> artistDTOSFlux = Flux.fromIterable(artistDTOList);

        Mockito.when(dataConsumer.consumeJson(any(), eq(ArtistDTO[].class))).thenReturn(artistDTOSFlux);

        Mono<ArtistDTO> result = eventService.processArtistInfoInFlux(artistId);

        StepVerifier.create(result)
                .expectError(ArtistNotFoundException.class)
                .verify();
    }





}

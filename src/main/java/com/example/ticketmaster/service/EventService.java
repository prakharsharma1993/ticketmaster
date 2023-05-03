package com.example.ticketmaster.service;


import com.example.ticketmaster.dao.DataConsumer;
import com.example.ticketmaster.dto.*;
import com.example.ticketmaster.exception.ArtistNotFoundException;
import com.example.ticketmaster.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {


    @Value("${event.data}")
    private String eventDataStream;

    @Value("${artist.data}")
    private String artistDataStream;

    @Value("${venue.data}")
    private String venueDataStream;

    @Autowired
    DataConsumer dataConsumer;

    public Mono<ArtistDTO> processArtistInfoInFlux(String artistId) throws IOException, InterruptedException {
        Flux<ArtistDTO> artistDTOSFlux = dataConsumer.consumeJson(artistDataStream, ArtistDTO[].class);
        Mono<List<ArtistDTO>> artistDTOSMono = artistDTOSFlux.collectList();

        return artistDTOSMono.flatMap(artistDTOS -> {
            Optional<ArtistDTO> artist = artistDTOS.stream().filter(artistDTO -> artistDTO.getId().equals(artistId)).findAny();
            if (artist.isPresent()) {
                try {
                    return processEventAndVenueInfoForArtist(artist.get());
                } catch (IOException | InterruptedException  e) {
                    return Mono.just(artist.get());
                }
            } else {
                throw new ArtistNotFoundException(HttpStatus.NOT_FOUND, "Data not found for artist ID :: "+artistId);
            }
        });
    }

    private Mono<ArtistDTO> processEventAndVenueInfoForArtist(ArtistDTO artistDTO) throws IOException, InterruptedException {
        Flux<EventDTO> eventDTOsFlux;
        Flux<VenueDTO> venueDTOSFlux;

        try {
            eventDTOsFlux = dataConsumer.consumeJson(eventDataStream, EventDTO[].class);
        } catch (IOException | InterruptedException e) {
            eventDTOsFlux = Flux.empty();
        }

        try {
            venueDTOSFlux = dataConsumer.consumeJson(venueDataStream, VenueDTO[].class);
        } catch (IOException | InterruptedException e) {
            venueDTOSFlux = Flux.empty();
        }

        Mono<List<EventDTO>> eventDTOsMono = eventDTOsFlux.collectList();
        Mono<List<VenueDTO>> venueDTOSMono = venueDTOSFlux.collectList();

        return Mono.zip(eventDTOsMono, venueDTOSMono)
                .flatMap(tuple -> {
                    List<EventDTO> eventDTOs = tuple.getT1();
                    List<VenueDTO> venueDTOS = tuple.getT2();

                    List<EventDTO> eventsWithArtist = eventDTOs.stream()
                            .filter(event -> event.getArtists().stream().anyMatch(eventArtist -> eventArtist.getId().equals(artistDTO.getId())))
                            .toList();

                    return Flux.fromIterable(eventsWithArtist)
                            .flatMap(eventDTO -> {
                                Optional<VenueDTO> venueDTO = venueDTOS.stream().filter(venueDTO1 -> venueDTO1.getId().equals(eventDTO.getVenue().getId())).findAny();
                                Event event = Event.builder().title(eventDTO.getTitle()).dateStatus(eventDTO.getDateStatus())
                                        .hiddenFromSearch(eventDTO.getHiddenFromSearch()).timeZone(eventDTO.getTimeZone())
                                        .startDate(eventDTO.getStartDate())
                                        .build();
                                if(venueDTO.isPresent())
                                {
                                    event.setVenueName(venueDTO.get().getName());
                                    event.setVenueUrl(venueDTO.get().getUrl());
                                    event.setCity(venueDTO.get().getCity());
                                }
                                return Mono.just(event);
                            })
                            .collectList()
                            .map(eventList -> {
                                artistDTO.setEvents(eventList);
                                return artistDTO;
                            });
                });
    }









}

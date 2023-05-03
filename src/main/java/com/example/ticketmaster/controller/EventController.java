package com.example.ticketmaster.controller;


import com.example.ticketmaster.dto.ArtistDTO;

import com.example.ticketmaster.exception.ArtistNotFoundException;
import com.example.ticketmaster.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("ticketmaster")
public class EventController {


    @Autowired
    EventService eventService;

    @GetMapping(value = "/{artistId}/eventDetails")
    public ResponseEntity<Mono<ArtistDTO>> getEventData(@PathVariable(required = true) Integer artistId) throws IOException, InterruptedException, ArtistNotFoundException {


            Mono<ArtistDTO> artistDTO = eventService.processArtistInfoInFlux(artistId.toString());
            return ResponseEntity.ok(artistDTO);

    }
}

package com.example.ticketmaster.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDTO {
    private String title;
    private String id;
    private String dateStatus;
    private String timeZone;
    private String startDate;
    private List<ArtistDTO> artists;
    private VenueDTO venue;
    private Boolean hiddenFromSearch;


}

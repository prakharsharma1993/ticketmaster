package com.example.ticketmaster.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import reactor.core.publisher.Flux;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArtistDTO {
 private String id;
    private String name;
    private String imgSrc;
    private String url;
    private String rank;
    private List<Event> events;

}

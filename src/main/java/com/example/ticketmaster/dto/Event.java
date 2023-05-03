package com.example.ticketmaster.dto;

import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String title;
    private String dateStatus;
    private String timeZone;
    private String startDate;
    private Boolean hiddenFromSearch;
    private String venueName;
    private String venueUrl;
    private String city;
}

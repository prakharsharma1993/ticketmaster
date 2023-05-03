package com.example.ticketmaster;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Person {

    Integer id;
    String name;
    Integer age;

}

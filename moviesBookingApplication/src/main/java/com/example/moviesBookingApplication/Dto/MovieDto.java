package com.example.moviesBookingApplication.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieDto {

    private  Long id;
    private String name;
    private String description;
    private String genre;
    private Integer duration;
    private LocalDate releaseDate;
    private String language;
}

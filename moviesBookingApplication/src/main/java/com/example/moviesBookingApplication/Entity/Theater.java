package com.example.moviesBookingApplication.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Theater {

    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;
    private String TheaterName;
    private String theaterLocation;
    private  String theaterCapacity;
    private String theaterScreenType;

    @OneToMany(mappedBy = "theater", fetch = FetchType.LAZY)
    private List<Show> show;
}

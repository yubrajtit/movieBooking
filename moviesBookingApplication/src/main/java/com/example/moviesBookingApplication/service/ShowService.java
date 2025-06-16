package com.example.moviesBookingApplication.service;

import com.example.moviesBookingApplication.Dto.ShowDTO;
import com.example.moviesBookingApplication.Entity.Booking;
import com.example.moviesBookingApplication.Entity.Movie;
import com.example.moviesBookingApplication.Entity.Show;
import com.example.moviesBookingApplication.Entity.Theater;
import com.example.moviesBookingApplication.Repository.MovieRepository;
import com.example.moviesBookingApplication.Repository.ShowRepository;
import com.example.moviesBookingApplication.Repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;


    public Show createShow (ShowDTO showDTO) {
        Movie movie = movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(()-> new RuntimeException("No movie found" +showDTO.getMovieId()));


        Theater theater = theaterRepository.findById(showDTO.getTheaterId())
                .orElseThrow(()-> new RuntimeException("No Theater found" +showDTO.getTheaterId()));


        Show show =  new Show();
        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheater(theater);

        return showRepository.save(show);
    }


    public List<Show> getAllShows() {
        return showRepository.findAll();

    }


    public List<Show> getShowsByMovie(Long movieid) {
       Optional<List<Show>> showListBox = showRepository.findByMovieId(movieid);
       if (showListBox.isPresent()){
           return showListBox.get();
       }
       else throw new RuntimeException("No shows available for the movie");
    }


    public List<Show> getShowsByTheater(Long theaterid) {
        Optional<List<Show>> showListBox = showRepository.findByTheaterId(theaterid);
        if (showListBox.isPresent()){
            return showListBox.get();
        }
        else throw new RuntimeException("No shows available for the theater");
    }

    public Show updateShow(Long id, ShowDTO showDTO) {
        Show show = showRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No show available for the id" + id));

        Movie movie = movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(()-> new RuntimeException("No movie found" +showDTO.getMovieId()));


        Theater theater = theaterRepository.findById(showDTO.getTheaterId())
                .orElseThrow(()-> new RuntimeException("No Theater found" +showDTO.getTheaterId()));

        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheater(theater);

        return showRepository.save(show);

    }


    public void deleteShow(Long id) {
        if (!showRepository.existsById(id)){
            throw new RuntimeException("No show available for the id" + id);
        }
        List<Booking> bookings = showRepository.findById(id).get().getBookings();
        if (bookings.isEmpty()){
            throw  new RuntimeException("Can't delete show with existing bookings");
        }
        showRepository.deleteById(id);
    }





















}


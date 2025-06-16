package com.example.moviesBookingApplication.service;

import com.example.moviesBookingApplication.Dto.MovieDto;
import com.example.moviesBookingApplication.Entity.Movie;
import com.example.moviesBookingApplication.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(MovieDto movieDto) {

        Movie movie = new Movie();
        movie.setName(movieDto.getName());
        movie.setDescription(movieDto.getDescription());
        movie.setGenre(movieDto.getGenre());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setDuration(movieDto.getDuration());
        movie.setLanguage(movieDto.getLanguage());

        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre) {
        Optional<List<Movie>> listOfMovieBox = movieRepository.findByGenre(genre);

        if (listOfMovieBox.isPresent()) {
            return listOfMovieBox.get();
        } else throw new RuntimeException("No movies founnd for genre" + genre);

    }

    public List<Movie> getMoviesByLanguage(String language) {
        Optional<List<Movie>> listOfMovieBox = movieRepository.findByLanguage(language);

        if (listOfMovieBox.isPresent()) {
            return listOfMovieBox.get();
        } else throw new RuntimeException("No movies found of language" + language);
    }

    public Movie getMoviesByTitle(String title) {
        Optional<Movie> movieBox = movieRepository.findByName(title);
        if (movieBox.isPresent()) {
            return movieBox.get();
        } else throw new RuntimeException("No movie found for the title" + title);
    }

    public Movie updateMovie(Long id, MovieDto movieDto) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No movie found for the id " + id));

        movie.setName(movieDto.getName());
        movie.setDescription(movieDto.getDescription());
        movie.setGenre(movieDto.getGenre());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setDuration(movieDto.getDuration());
        movie.setLanguage(movieDto.getLanguage());

        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
       movieRepository.deleteById(id);
    }
}
package com.example.moviesBookingApplication.controller;

import com.example.moviesBookingApplication.Dto.MovieDto;
import com.example.moviesBookingApplication.Entity.Movie;
import com.example.moviesBookingApplication.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @PostMapping("/addmovie")
    @PreAuthorize("haseRole('ADMIN')")
    public ResponseEntity<Movie> addMovie(@RequestBody MovieDto movieDto ){
        return ResponseEntity.ok(movieService.addMovie(movieDto));
    }

    @GetMapping("/getallmovie")
    public ResponseEntity<List<Movie>> getAllMovie(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/getmoviesbygenre")
    public ResponseEntity<List<Movie>> getAllMoviesByGenre(@RequestParam String genre){
       return ResponseEntity.ok(movieService.getMoviesByGenre(genre));
    }


    @GetMapping("/getmoviesbylanguage")
    public ResponseEntity<List<Movie>> getMoviesByLanguage(@RequestParam String language){
        return ResponseEntity.ok(movieService.getMoviesByLanguage(language));
    }


    @GetMapping("/getmoviesbytitle")
    public ResponseEntity<Movie> getMoviesByTitle(@RequestParam String title){
        return ResponseEntity.ok(movieService.getMoviesByTitle(title));
    }

    @PutMapping("/updatemovie/{id}")
    @PreAuthorize("haseRole('ADMIN')")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody MovieDto movieDto){
        return ResponseEntity.ok(movieService.updateMovie(id,movieDto));
    }

    @DeleteMapping("deletemovie{id}")
    @PreAuthorize("haseRole('ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return  ResponseEntity.ok().build();
    }
}

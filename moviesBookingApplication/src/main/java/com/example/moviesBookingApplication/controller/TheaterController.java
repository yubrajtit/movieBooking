package com.example.moviesBookingApplication.controller;

import com.example.moviesBookingApplication.Dto.TheaterDTO;
import com.example.moviesBookingApplication.Entity.Theater;
import com.example.moviesBookingApplication.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/theater")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @PostMapping("/addtheater")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theater> addTheater(@RequestBody TheaterDTO theaterDTO){
        return ResponseEntity.ok(theaterService.addTheater(theaterDTO));
    }

    @GetMapping("/gettheaterlocation")
    public ResponseEntity<Theater> getTheaterLocation(@RequestParam String location){
        return ResponseEntity.ok((Theater) theaterService.getTheaterLocation(location));
    }



    @PutMapping("/updatetheater{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<Theater>  updateTheater(@PathVariable Long id, @RequestBody TheaterDTO theaterDTO){
        return ResponseEntity.ok(theaterService.updateTheater(id,theaterDTO));
    }

    @DeleteMapping("/deletetheater{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTheater(@PathVariable Long id){
        theaterService.deleteTheater(id);
        return ResponseEntity.ok().build();
    }



















}

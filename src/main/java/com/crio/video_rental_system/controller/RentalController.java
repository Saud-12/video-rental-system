package com.crio.video_rental_system.controller;

import com.crio.video_rental_system.dto.RentalDto;
import com.crio.video_rental_system.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/{userId}/videos/{videoId}")
    public ResponseEntity<RentalDto> rentVideo(@PathVariable Long userId, @PathVariable Long videoId){
        return new ResponseEntity(rentalService.rentVideo(userId,videoId), HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/rental/{rentalId}")
    public ResponseEntity<Void> returnVideo(@PathVariable Long userId,@PathVariable Long rentalId){
        rentalService.returnVideo(userId,rentalId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RentalDto>> getUserRentals(@PathVariable Long userId){
        return ResponseEntity.ok(rentalService.getUserRentals(userId));
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<RentalDto>> getActiveRentals(@PathVariable Long userId){
        return ResponseEntity.ok(rentalService.getActiveRentals(userId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/video/{videoId}")
    public ResponseEntity<List<RentalDto>> getRentalsByVideo(@PathVariable Long videoId){
        return ResponseEntity.ok(rentalService.getRentalsByVideo(videoId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<RentalDto>> getAllRentals(){
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{rentalId}")
    public ResponseEntity<String> deleteRental(@PathVariable Long rentalId){
        rentalService.deleteRental(rentalId);
        return ResponseEntity.ok("Rental successfully deleted.");
    }

}

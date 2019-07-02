package com.example.airbnb.controllers;

import java.util.List;
import com.example.airbnb.entities.Booking;
import com.example.airbnb.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BookingController
 */
@RestController
@RequestMapping(path="/api")
public class BookingController {
    @Autowired
    BookingRepository bookingRepository;

    @GetMapping(value="/booking", produces ="application/json")
    public List<Booking> displayBookings() {
        return bookingRepository.getAllBookings();
    }
    
    @GetMapping(path="/properties/{id}/bookings", produces="application/json")
    public List<Booking> displayBookingsByPropertyId(@PathVariable("id") int id){
        return bookingRepository.getBookingsByPropertyId(id);
    }
    
    @PostMapping(path="/bookings/{id}", produces="application/json")
    public Booking updateTotalPrice(@PathVariable("id") int id, @RequestBody Booking booking) {
        bookingRepository.updateTotalPrice(id,booking);
        return booking;
    }
}
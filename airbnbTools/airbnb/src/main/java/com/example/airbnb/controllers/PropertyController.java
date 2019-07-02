package com.example.airbnb.controllers;

import java.util.List;

import com.example.airbnb.entities.Property;
import com.example.airbnb.repositories.PropertyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * PropertyController
 */
@RestController
@RequestMapping(path="/api")
public class PropertyController {
    @Autowired
    PropertyRepository propertyRepository;

    @GetMapping(value="/property", produces ="application/json")
    public List<Property> displayPropertys() {
        return propertyRepository.getAllPropertys();
    }
    
    @GetMapping(path="/properties_address", produces="application/json")
    public List<Property> displayPropertyByAddress(@RequestParam String address){
        return propertyRepository.getPropertyByAddress(address);
    }

    @GetMapping(path="/properties_numRooms", produces="application/json")
    public List<Property> displayPropertyByNumRooms(@RequestParam int numRooms){
        return propertyRepository.getPropertyByNumRooms(numRooms);
    }
 
    @GetMapping(path="/properties_price", produces="application/json")
    public List<Property> displayPropertyByPrice(@RequestParam int price){
        return propertyRepository.getPropertyByPrice(price);
    }

    @GetMapping(path="/properties", produces="application/json")
    public List<Property> searchProperties(
        @RequestParam("address") String address,
        @RequestParam("numRooms") int numRooms,
        @RequestParam("price") int price
        ){
        return propertyRepository.searchProperties(address, numRooms, price);
    }
    
    @GetMapping(path="/bookedProperties", produces="application/json")
    public List<Property> displayBookedProperties(){
        return propertyRepository.getBookedProperties();
    }

    @GetMapping(path="/reviewedProperties", produces="application/json")
    public List<Property> displayReviewedProperties(){
        return propertyRepository.getReviewedProperties();
    }

    // @GetMapping(path="/create_property", produces="application/json")
    // public void addProperty(){
    //     propertyRepository.addProperty("123, test address", 2, 15000, false, 8);
    // }
    @GetMapping(path="/create_property", produces="application/json")
    public void createProperty(
        @RequestParam("address") String address,
        @RequestParam("numRooms") int numRooms,
        @RequestParam("price") int price,
        @RequestParam("allowSmoking") boolean allowSmoking,
        @RequestParam("maxGuestNum") int maxGuestNum
    ){
        Property property = new Property();
        property.setAddress(address);
        property.setAllowSmoking(allowSmoking);
        property.setMaxGuestNum(maxGuestNum);
        property.setNumRooms(numRooms);
        property.setPrice(price);

        propertyRepository.createProperty(property);
    }

    @GetMapping(path="/update_property")
    public void updateProperty(){
        propertyRepository.updateProperty(true,false);
    }

    @GetMapping(path="/delete_property")
    public void deleteProperty(){
        propertyRepository.deleteProperty(6);
    }

    @PostMapping(value="/properties", produces="application/json")
    public Property createProperties(@RequestBody Property property) {
        propertyRepository.createProperties(property);
        return property;
    }
}
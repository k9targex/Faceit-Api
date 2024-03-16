package com.faceit.faceit.controller;



import com.faceit.faceit.service.CountryService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/country")

public class CountryController {
    private CountryService countryService;
    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }
    @GetMapping("/getCountryUsers")
    public ResponseEntity<?> getCountryUsers(@RequestParam String countryName) {
        try
        {
            return ResponseEntity.ok().body(countryService.getCountryUsers(countryName));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getCountries")
    public ResponseEntity<?> getCountries() {
        try {
            return ResponseEntity.ok().body(countryService.getCountries());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PatchMapping("/editCountryName")
    public ResponseEntity<?> editCountryName(@RequestParam String countryName, String newCountryName){
        try {
            countryService.editCountryName(countryName,newCountryName);
            return ResponseEntity.ok().body("Succes bitch");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/deleteCountry")
    public ResponseEntity<?> deleteCountry(@RequestParam String countryName){
        try {
            countryService.deleteCountry(countryName);
            return ResponseEntity.ok().body("Succes bitch");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
package com.faceit.faceit.service;

import com.faceit.faceit.dao.CountryRepository;

import com.faceit.faceit.model.entity.Country;
import com.faceit.faceit.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Service
@Transactional
public class CountryService {
    private CountryRepository countryRepository;

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<User> getCountryUsers(String countryName)
    {
        if (countryRepository.findCountryByCountryName(countryName)==null)
        {
            throw new IllegalArgumentException("Country doesn't exist");
        }
        Country country = countryRepository.findCountryByCountryName(countryName);
        return country.getUsers();
    }
    public List<Country> getCountries(){
        return countryRepository.findAll();
    }
    public void editCountryName(String countryName,String newCountryName) {
        if (countryRepository.findCountryByCountryName(countryName)==null)
        {
            throw new IllegalArgumentException("Country doesn't exist");
        }
        Country country= countryRepository.findCountryByCountryName(countryName);
        country.setCountryName(newCountryName);
    }
    public void deleteCountry(String countryName){
        if (countryRepository.findCountryByCountryName(countryName)==null)
        {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST,"Страна не найдена");
        }
        else
            if(countryRepository.findCountryByCountryName(countryName).getUsers().isEmpty())
                countryRepository.delete(countryRepository.findCountryByCountryName(countryName));

    }

}
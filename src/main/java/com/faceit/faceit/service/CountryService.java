package com.faceit.faceit.service;

import com.faceit.faceit.dao.CountryRepository;
import com.faceit.faceit.dao.UserRepository;
import com.faceit.faceit.entity.Country;
import com.faceit.faceit.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CountryService {
    private CountryRepository countryRepository;
    private UserRepository userRepository;
    @Autowired
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getCountryUsers(String countryName) throws Exception
    {
        if (countryRepository.findCountryByCountryName(countryName)==null)
        {
            throw new Exception("Country doesn't exist");
        }
        Country country = countryRepository.findCountryByCountryName(countryName);
        return country.getUsers();
    }
    public List<Country> getCountries(){
        return countryRepository.findAll();
    }
    public void editCountryName(String countryName,String newCountryName) throws Exception{
        Country country= countryRepository.findCountryByCountryName(countryName);
        country.setCountryName(newCountryName);
    }
    public void deleteCountry(String countyName){
        Country country= countryRepository.findCountryByCountryName(countyName);
        countryRepository.delete(country);
    }

}
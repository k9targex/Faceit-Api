package com.faceit.faceit.service;

import com.faceit.faceit.dao.CountryRepository;
import com.faceit.faceit.exception.CountryNotFoundException;
import com.faceit.faceit.model.entity.Country;
import com.faceit.faceit.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountryService {

  private static final String COUNTRY_NOT_FOUND_MESSAGE = "Country \"%s\" doesn't exist";
  private CountryRepository countryRepository;

  @Autowired
  public void setCountryRepository(CountryRepository countryRepository) {
    this.countryRepository = countryRepository;
  }

  public List<User> getCountryUsers(String countryName) {

    Country country =
        countryRepository
            .findCountryByCountryName(countryName)
            .orElseThrow(
                () ->
                    new CountryNotFoundException(
                        String.format(COUNTRY_NOT_FOUND_MESSAGE, countryName)));
    return country.getUsers();
  }

  public List<Country> getCountries() {
    return countryRepository.findAll();
  }

  public void editCountryName(String countryName, String newCountryName) {
    Country currentCountry =
        countryRepository
            .findCountryByCountryName(countryName)
            .orElseThrow(
                () ->
                    new CountryNotFoundException(
                        String.format(COUNTRY_NOT_FOUND_MESSAGE, countryName)));

    Optional<Country> newCountryOptional =
        countryRepository.findCountryByCountryName(newCountryName);
    if (newCountryOptional.isPresent()) {
      Country newCountry = newCountryOptional.get();
      newCountry.getUsers().addAll(currentCountry.getUsers());
      currentCountry.getUsers().forEach(user -> user.setCountry(newCountry));
      countryRepository.save(newCountry);
      deleteCountry(countryName);
    } else {
      currentCountry.setCountryName(newCountryName);
      countryRepository.save(currentCountry);
    }
  }

  public void deleteCountry(String countryName) {

    countryRepository.delete(
        countryRepository
            .findCountryByCountryName(countryName)
            .orElseThrow(
                () ->
                    new CountryNotFoundException(
                        String.format(COUNTRY_NOT_FOUND_MESSAGE, countryName))));
  }
}

package com.faceit.faceit.dao;

import com.faceit.faceit.model.entity.Country;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
  Optional<Country> findCountryByCountryName(String country);
}

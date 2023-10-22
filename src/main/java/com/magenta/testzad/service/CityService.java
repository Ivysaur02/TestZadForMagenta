package com.magenta.testzad.service;


import com.magenta.testzad.entity.City;
import com.magenta.testzad.exseption.NoSuchCityException;
import com.magenta.testzad.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<City> getAllCity() {
        return cityRepository.findAll();
    }

    public City getCityByID(int id) {
        return cityRepository.findById(id).orElseThrow(() -> new NoSuchCityException(id));
    }

}

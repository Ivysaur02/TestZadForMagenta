package com.magenta.testzad.controller;


import com.magenta.testzad.dto.CitiesDistancesDtoParser;
import com.magenta.testzad.dto.CityDto;
import com.magenta.testzad.dto.CityDtoRequest;
import com.magenta.testzad.entity.City;
import com.magenta.testzad.service.CityService;
import com.magenta.testzad.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping

public class DistanceCalculatorController {
    @Autowired
    private CityService cityService;

    @Autowired
    private DistanceService distanceService;
    @Autowired
    private CitiesDistancesDtoParser citiesDistancesDto;


    @GetMapping("/allCities")
    public List<CityDtoRequest> allCities() {
        List<City> find = cityService.getAllCity();
        return find.stream()
                .map(city -> CityDtoRequest.builder()
                        .id(city.getId())
                        .name(city.getName())
                        .build())
                .collect(Collectors.toList());

    }
}

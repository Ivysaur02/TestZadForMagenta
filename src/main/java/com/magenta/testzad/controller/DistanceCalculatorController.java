package com.magenta.testzad.controller;


import com.magenta.testzad.dto.CitiesDistancesDtoParser;
import com.magenta.testzad.dto.CityDtoRequest;
import com.magenta.testzad.entity.City;
import com.magenta.testzad.service.CityService;
import com.magenta.testzad.service.DistanceService;
import com.magenta.testzad.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private StorageService storageService;


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


    @PostMapping(value = "/addCityAndDistance", consumes = "multipart/form-data")
    public ResponseEntity<?> addCityAndDistance(@RequestParam("file") MultipartFile file) {
        storageService.store(file, "src/main/resources/downloadfiles/");

        citiesDistancesDto.unmarshal("src/main/resources/downloadfiles/" + file.getOriginalFilename());

        cityService.addCities(citiesDistancesDto.cityDTOListToCityList());
        distanceService.addDistances(citiesDistancesDto.distanceDTOListToDistanceList());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}


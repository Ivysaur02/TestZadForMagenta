package com.magenta.testzad.controller;


import com.magenta.testzad.dto.CalculatedDistanceDtoRequest;
import com.magenta.testzad.dto.CitiesDistancesDtoParser;
import com.magenta.testzad.dto.CityDtoRequest;
import com.magenta.testzad.entity.City;
import com.magenta.testzad.entity.Distance;
import com.magenta.testzad.service.CityService;
import com.magenta.testzad.service.DistanceService;
import com.magenta.testzad.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    @PostMapping(value = "/calculatedDistance")
    public List<CalculatedDistanceDtoRequest> calculatedDistances(@RequestBody List<Distance> distanceList,
                                                                  @RequestParam(name = "type") String type) {

        List<CalculatedDistanceDtoRequest> results = new ArrayList<>();

        for (Distance distance : distanceList) {
            CalculatedDistanceDtoRequest result = new CalculatedDistanceDtoRequest();
            result.setIdFromCity(distance.getIdFromCity());
            result.setIdToCity(distance.getIdToCity());

            // Perform the Crowflight calculation
            if (type.equals("Crowflight") || type.equals("All")) {
                double crowflightDistance = distanceService.calculateCrowFlightDistance(distance.getIdFromCity(), distance.getIdToCity());
                result.setDistanceCrow(crowflightDistance);
            }

            // Perform the Distance Matrix calculation
            if (type.equals("Distance Matrix") || type.equals("All")) {
                double distanceMatrixDistance = distanceService.getDistanceBetweenTwoCity(distance.getIdFromCity(), distance.getIdToCity());
                result.setDistanceMatrix(distanceMatrixDistance);
            }

            results.add(result);
        }

        return results;
    }
}


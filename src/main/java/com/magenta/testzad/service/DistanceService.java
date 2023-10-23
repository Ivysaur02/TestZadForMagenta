package com.magenta.testzad.service;


import com.magenta.testzad.entity.City;
import com.magenta.testzad.entity.Distance;
import com.magenta.testzad.repository.DistanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistanceService {

    private final CityService cityService;

    private final DistanceRepository distanceRepository;


    public void addDistances(List<Distance> distanceList) {
        for (Distance distance : distanceList) {
            if (distance.getDistance() != -1) {
                distanceRepository.addDistance(distance.getIdFromCity(), distance.getIdToCity(), distance.getDistance());
            }
        }
    }

    public double calculateCrowFlightDistance(int id_city1, int id_city2) {

        City city1 = cityService.getCityByID(id_city1);
        City city2 = cityService.getCityByID(id_city2);
        int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(city2.getLatitude() - city1.getLatitude());
        double lonDistance = Math.toRadians(city2.getLongitude() - city1.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(city1.getLatitude())) * Math.cos(Math.toRadians(city2.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c * 1000;
    }

    public Double getDistanceBetweenTwoCity(int id_city1, int id_city2) {
        Distance distance = distanceRepository.findByFromCityIdAndToCityId(id_city1, id_city2);
        if (distance == null) {
            log.info("No way between cities " + id_city1 + " and " + id_city2);
            return null;
        }
        return distance.getDistance();
    }
}

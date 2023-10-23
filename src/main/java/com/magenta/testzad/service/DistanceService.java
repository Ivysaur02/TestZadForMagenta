package com.magenta.testzad.service;


import com.magenta.testzad.entity.City;
import com.magenta.testzad.entity.Distance;
import com.magenta.testzad.repository.DistanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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

    public double calculateCrowFlightDistance(City city1, City city2) {


        int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(city2.getLatitude() - city1.getLatitude());
        double lonDistance = Math.toRadians(city2.getLongitude() - city1.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(city1.getLatitude())) * Math.cos(Math.toRadians(city2.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c * 1000;
    }

    public double getDistanceBetweenTwoCity() {
        return -1;
    }
}

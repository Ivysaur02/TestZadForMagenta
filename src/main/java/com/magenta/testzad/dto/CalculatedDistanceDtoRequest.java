package com.magenta.testzad.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class CalculatedDistanceDtoRequest {

    private int idFromCity;
    private int idToCity;
    private double distanceCrow;
    private double distanceMatrix;


}

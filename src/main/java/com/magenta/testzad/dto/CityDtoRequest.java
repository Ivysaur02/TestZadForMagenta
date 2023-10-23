package com.magenta.testzad.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CityDtoRequest {

    private int id;
    private String name;
}

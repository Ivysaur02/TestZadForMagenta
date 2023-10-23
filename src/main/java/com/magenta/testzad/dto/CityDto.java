package com.magenta.testzad.dto;


import com.magenta.testzad.entity.City;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "city")
@XmlType(propOrder = {"id", "name", "latitude", "longitude"})
@Setter
@Getter
@Builder
public class CityDto implements Cloneable {

    @XmlAttribute
    private int id;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private int latitude;

    @XmlAttribute
    private int longitude;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public City toCity(){
        return City.builder()
                .id(id)
                .name(name)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}

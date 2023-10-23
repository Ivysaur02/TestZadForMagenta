package com.magenta.testzad.dto;


import com.magenta.testzad.entity.City;
import lombok.*;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "city")
@XmlType(propOrder = {"id", "name", "latitude", "longitude"})
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDto implements Cloneable {

    @XmlAttribute
    private int id;
    @XmlElement
    private String name;
    @XmlElement
    private int latitude;

    @XmlElement
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

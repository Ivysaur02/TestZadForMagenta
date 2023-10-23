package com.magenta.testzad.dto;


import com.magenta.testzad.entity.Distance;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "distance")
@XmlType(propOrder = {"id", "distance", "idFromCity", "idToCity"})
@Getter
@Setter
@Builder
public class DistanceDto implements Cloneable {

    @XmlElement
    private int id;
    @XmlElement
    private int distance;
    @XmlElement
    private int idFromCity;
    @XmlElement
    private int idToCity;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Distance toDistance() {
        return Distance.builder()
                .id(id)
                .idFromCity(idFromCity)
                .idToCity(idToCity)
                .distance(distance)
                .build();
    }

}

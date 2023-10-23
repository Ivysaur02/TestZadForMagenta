package com.magenta.testzad.dto;


import com.magenta.testzad.entity.Distance;
import lombok.*;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "distance")
@XmlType(propOrder = {"id", "distance", "idFromCity", "idToCity"})
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistanceDto implements Cloneable {

    @XmlAttribute
    private int id;
    @XmlElement
    private double distance;
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

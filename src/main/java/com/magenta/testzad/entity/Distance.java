package com.magenta.testzad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "distance")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Distance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_from_city", updatable = false, insertable = false, nullable = false)
    private int idFromCity;

    @Column(name = "id_to_city", updatable = false, insertable = false, nullable = false)
    private int idToCity;

    @Column(name = "distance")
    private double distance;
}

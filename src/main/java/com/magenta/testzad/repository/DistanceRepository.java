package com.magenta.testzad.repository;

import com.magenta.testzad.entity.Distance;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DistanceRepository extends JpaRepository<Distance, Integer> {

    @Modifying
    @Query(value = "INSERT INTO distance (id_from_city, id_to_city, distance) VALUES (:in_id_from_city, :in_id_to_city, :in_distance)", nativeQuery = true)
    @Transactional
    public void addDistance(@Param("in_id_from_city") long idFromCity, @Param("in_id_to_city") long idToCity, @Param("in_distance") double distance);

//    @Query("SELECT * FROM Distance d WHERE d.idFromCity = :fromCityId AND d.idToCity = :toCityId")
//    Distance findByFromCityIdAndToCityId(@Param("fromCityId") int fromCityId, @Param("toCityId") int toCityId);

    Distance findByidFromCityAndIdToCity(int idFromCity, int idToCity);
}

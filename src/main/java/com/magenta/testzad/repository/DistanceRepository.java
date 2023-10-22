package com.magenta.testzad.repository;

import com.magenta.testzad.entity.Distance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DistanceRepository extends JpaRepository<Distance, Integer> {
}

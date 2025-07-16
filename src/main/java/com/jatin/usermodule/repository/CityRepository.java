package com.jatin.usermodule.repository;

import com.jatin.usermodule.entity.City;
import com.jatin.usermodule.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByState(State state);
}

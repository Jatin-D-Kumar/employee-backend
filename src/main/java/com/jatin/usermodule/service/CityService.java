package com.jatin.usermodule.service;

import com.jatin.usermodule.dto.CityDTO;

import java.util.List;

public interface CityService {

    /**
     * Get cities by state name (e.g., "Delhi", "UP").
     *
     * @param stateName the name of the state
     * @return list of cities belonging to the state
     */
    List<CityDTO> getCitiesByStateName(String stateName);
}

package com.jatin.usermodule.serviceImpl;

import com.jatin.usermodule.dto.CityDTO;
import com.jatin.usermodule.entity.City;
import com.jatin.usermodule.entity.State;
import com.jatin.usermodule.repository.CityRepository;
import com.jatin.usermodule.repository.StateRepository;
import com.jatin.usermodule.service.CityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CityDTO> getCitiesByStateName(String stateName) {
        Optional<State> stateOpt = stateRepository.findByNameIgnoreCase(stateName);
        if (stateOpt.isEmpty()) {
            return Collections.emptyList(); // or throw exception if preferred
        }

        List<City> cities = cityRepository.findByState(stateOpt.get());

        return cities.stream()
                .map(city -> modelMapper.map(city, CityDTO.class))
                .collect(Collectors.toList());
    }
}

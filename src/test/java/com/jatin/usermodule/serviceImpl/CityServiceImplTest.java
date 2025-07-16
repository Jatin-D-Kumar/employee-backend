package com.jatin.usermodule.serviceImpl;

import com.jatin.usermodule.dto.CityDTO;
import com.jatin.usermodule.entity.City;
import com.jatin.usermodule.entity.State;
import com.jatin.usermodule.repository.CityRepository;
import com.jatin.usermodule.repository.StateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

    @Mock
    private StateRepository stateRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CityServiceImpl cityService;

    @Test
    void getCitiesByStateName_shouldReturnMappedCities() {
        State state = new State("Delhi", null);
        City city = new City("New Delhi", state);

        when(stateRepository.findByNameIgnoreCase("Delhi")).thenReturn(Optional.of(state));
        when(cityRepository.findByState(state)).thenReturn(List.of(city));
        when(modelMapper.map(city, CityDTO.class)).thenReturn(new CityDTO(UUID.randomUUID().toString(), "New Delhi"));

        List<CityDTO> result = cityService.getCitiesByStateName("Delhi");

        assertEquals(1, result.size());
        assertEquals("New Delhi", result.get(0).getName());
    }

    @Test
    void getCitiesByInvalidState_shouldReturnEmptyList() {
        when(stateRepository.findByNameIgnoreCase("Invalid")).thenReturn(Optional.empty());

        List<CityDTO> result = cityService.getCitiesByStateName("Invalid");

        assertTrue(result.isEmpty());
    }
}

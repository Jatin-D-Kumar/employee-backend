package com.jatin.usermodule.controller;

import com.jatin.usermodule.dto.CityDTO;
import com.jatin.usermodule.dto.StateDTO;
import com.jatin.usermodule.service.CityService;
import com.jatin.usermodule.service.StateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StateCityController.class)
class StateCityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StateService stateService;

    @Autowired
    private CityService cityService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public StateService stateService() {
            return Mockito.mock(StateService.class);
        }

        @Bean
        public CityService cityService() {
            return Mockito.mock(CityService.class);
        }
    }

    @Test
    void testGetAllStates() throws Exception {
        List<StateDTO> states = List.of(
                new StateDTO(UUID.randomUUID().toString(), "Delhi"),
                new StateDTO(UUID.randomUUID().toString(), "Uttar Pradesh"));

        Mockito.when(stateService.getAllStates()).thenReturn(states);

        mockMvc.perform(get("/api/states"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(2)))
                .andExpect(jsonPath("$.data[0].name", is("Delhi")))
                .andExpect(jsonPath("$.data[1].name", is("Uttar Pradesh")));
    }

    @Test
    void testGetCitiesByStateName() throws Exception {
        List<CityDTO> cities = List.of(
                new CityDTO(UUID.randomUUID().toString(), "Delhi"),
                new CityDTO(UUID.randomUUID().toString(), "New Delhi"));

        Mockito.when(cityService.getCitiesByStateName("Delhi")).thenReturn(cities);

        mockMvc.perform(get("/api/cities")
                .param("state", "Delhi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(2)))
                .andExpect(jsonPath("$.data[0].name", is("Delhi")))
                .andExpect(jsonPath("$.data[1].name", is("New Delhi")));
    }
}

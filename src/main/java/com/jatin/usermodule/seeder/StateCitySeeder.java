package com.jatin.usermodule.seeder;

import com.jatin.usermodule.entity.City;
import com.jatin.usermodule.entity.State;
import com.jatin.usermodule.repository.CityRepository;
import com.jatin.usermodule.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
@RequiredArgsConstructor
public class StateCitySeeder {

    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    @Bean
    public ApplicationRunner seedStatesAndCities() {
        return args -> {
            if (stateRepository.count() == 0) {
                // Define states and cities
                Map<String, List<String>> data = Map.of(
                        "Delhi", List.of("Delhi", "New Delhi"),
                        "Uttar Pradesh", List.of("Noida", "Lucknow"));

                data.forEach((stateName, cities) -> {
                    State state = State.builder().name(stateName).build();
                    state = stateRepository.save(state);

                    for (String cityName : cities) {
                        City city = City.builder().name(cityName).state(state).build();
                        cityRepository.save(city);
                    }
                });

                System.out.println("State and city data seeded.");
            } else {
                System.out.println("States and cities already exist. Skipping seeding.");
            }
        };
    }
}

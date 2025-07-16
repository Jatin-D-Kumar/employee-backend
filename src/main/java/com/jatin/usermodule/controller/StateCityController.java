package com.jatin.usermodule.controller;

import com.jatin.usermodule.dto.CityDTO;
import com.jatin.usermodule.dto.StateDTO;
import com.jatin.usermodule.service.CityService;
import com.jatin.usermodule.service.StateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "State & City APIs", description = "Endpoints for dynamic state and city dropdowns")
public class StateCityController {

    private final StateService stateService;
    private final CityService cityService;

    @GetMapping("/states")
    @Operation(summary = "Get all states", description = "Returns a list of all available states", responses = {
            @ApiResponse(responseCode = "200", description = "List of states", content = @Content(array = @ArraySchema(schema = @Schema(implementation = StateDTO.class))))
    })
    public ResponseEntity<List<StateDTO>> getStates() {
        return ResponseEntity.ok(stateService.getAllStates());
    }

    @GetMapping("/cities")
    @Operation(summary = "Get cities by state name", description = "Returns a list of cities for the provided state name (e.g., 'Delhi')", responses = {
            @ApiResponse(responseCode = "200", description = "List of cities", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CityDTO.class)))),
            @ApiResponse(responseCode = "404", description = "State not found")
    })
    public ResponseEntity<List<CityDTO>> getCitiesByState(
            @RequestParam String state) {
        List<CityDTO> cities = cityService.getCitiesByStateName(state);
        return ResponseEntity.ok(cities);
    }
}

package com.jatin.usermodule.controller;

import com.jatin.usermodule.dto.UserRequestDTO;
import com.jatin.usermodule.dto.UserResponseDTO;
import com.jatin.usermodule.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User APIs", description = "Operations related to user registration and listing")
public class UserController {

        private final UserService userService;

        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @Operation(summary = "Register new user", description = "Registers a new user with form data including image upload", responses = {
                        @ApiResponse(responseCode = "201", description = "User registered successfully"),
                        @ApiResponse(responseCode = "400", description = "Validation error or bad request")
        })
        public ResponseEntity<UserResponseDTO> registerUser(
                        @Valid @ModelAttribute UserRequestDTO userRequestDTO) {
                UserResponseDTO saved = userService.register(userRequestDTO);
                return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }

        @GetMapping
        @Operation(summary = "Get list of users", description = "Returns list of users with optional filters for name, gender, and state", responses = {
                        @ApiResponse(responseCode = "200", description = "List returned successfully")
        })
        public ResponseEntity<List<UserResponseDTO>> getUsers(
                        @Parameter(description = "Partial name match") @RequestParam(required = false) String name,
                        @Parameter(description = "Gender filter") @RequestParam(required = false) String gender,
                        @Parameter(description = "State filter") @RequestParam(required = false) String state) {
                List<UserResponseDTO> users = userService.getUsers(name, gender, state);
                return ResponseEntity.ok(users);
        }
}

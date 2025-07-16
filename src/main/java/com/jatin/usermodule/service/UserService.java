package com.jatin.usermodule.service;

import com.jatin.usermodule.dto.UserRequestDTO;
import com.jatin.usermodule.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    /**
     * Registers a new user with validations (photo, regId, contact check).
     *
     * @param userDto user form data
     * @return saved user response
     */
    UserResponseDTO register(UserRequestDTO userDto);

    /**
     * Returns list of users with optional filters: name, gender, state.
     *
     * @param name   partial match for fullName
     * @param gender gender filter
     * @param state  state filter
     * @return filtered user list
     */
    List<UserResponseDTO> getUsers(String name, String gender, String state);
}

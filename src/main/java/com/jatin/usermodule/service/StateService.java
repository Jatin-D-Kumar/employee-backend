package com.jatin.usermodule.service;

import com.jatin.usermodule.dto.StateDTO;

import java.util.List;

public interface StateService {

    /**
     * Get list of all available states.
     *
     * @return list of states
     */
    List<StateDTO> getAllStates();
}

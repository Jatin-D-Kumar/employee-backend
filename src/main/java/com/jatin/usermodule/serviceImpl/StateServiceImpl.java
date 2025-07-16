package com.jatin.usermodule.serviceImpl;

import com.jatin.usermodule.dto.StateDTO;
import com.jatin.usermodule.entity.State;
import com.jatin.usermodule.repository.StateRepository;
import com.jatin.usermodule.service.StateService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<StateDTO> getAllStates() {
        List<State> states = stateRepository.findAll();

        return states.stream()
                .map(state -> modelMapper.map(state, StateDTO.class))
                .collect(Collectors.toList());
    }
}

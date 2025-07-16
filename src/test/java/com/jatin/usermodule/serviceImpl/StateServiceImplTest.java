package com.jatin.usermodule.serviceImpl;

import com.jatin.usermodule.dto.StateDTO;
import com.jatin.usermodule.entity.State;
import com.jatin.usermodule.repository.StateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StateServiceImplTest {

    @Mock
    private StateRepository stateRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StateServiceImpl stateService;

    @Test
    void getAllStates_shouldReturnMappedDTOs() {
        State state = new State();
        state.setName("Delhi");

        when(stateRepository.findAll()).thenReturn(List.of(state));
        when(modelMapper.map(state, StateDTO.class)).thenReturn(new StateDTO(UUID.randomUUID().toString(), "Delhi"));

        List<StateDTO> result = stateService.getAllStates();

        assertEquals(1, result.size());
        assertEquals("Delhi", result.get(0).getName());
    }
}

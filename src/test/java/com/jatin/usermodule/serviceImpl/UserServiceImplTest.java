package com.jatin.usermodule.serviceImpl;

import com.jatin.usermodule.dto.UserRequestDTO;
import com.jatin.usermodule.dto.UserResponseDTO;
import com.jatin.usermodule.entity.User;
import com.jatin.usermodule.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void registerUser_withValidData_shouldReturnResponseDTO() {
        // Arrange
        UserRequestDTO dto = UserRequestDTO.builder()
                .fullName("Jatin")
                .gender("Male")
                .dob("01/01/2000")
                .email("jatin@example.com")
                .mobile("9876543210")
                .state("Delhi")
                .city("New Delhi")
                .build();

        User savedUser = User.builder()
                .fullName(dto.getFullName())
                .gender(dto.getGender())
                .dob(dto.getDob())
                .mobile(dto.getMobile())
                .state(dto.getState())
                .city(dto.getCity())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(modelMapper.map(any(User.class), eq(UserResponseDTO.class)))
                .thenReturn(UserResponseDTO.builder().regId("REG00000001").build());

        ReflectionTestUtils.setField(userService, "uploadDir", "uploads");

        // Act
        UserResponseDTO result = userService.register(dto);

        // Assert
        assertNotNull(result);
        assertEquals("REG00000001", result.getRegId());
        verify(userRepository, times(2)).save(any(User.class)); // First save then regId update
    }

    @Test
    void getUsers_shouldFilterAndMapCorrectly() {
        User user = User.builder().fullName("Jatin").gender("Male").state("Delhi").build();

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(any(User.class), eq(UserResponseDTO.class)))
                .thenReturn(UserResponseDTO.builder().fullName("Jatin").build());

        List<UserResponseDTO> users = userService.getUsers("jat", "Male", "Delhi");

        assertEquals(1, users.size());
        assertEquals("Jatin", users.get(0).getFullName());
    }
}

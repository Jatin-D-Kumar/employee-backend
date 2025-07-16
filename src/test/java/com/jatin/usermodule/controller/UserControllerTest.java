package com.jatin.usermodule.controller;

import com.jatin.usermodule.dto.UserResponseDTO;
import com.jatin.usermodule.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    @Test
    void testRegisterUser() throws Exception {
        // Prepare mock input
        MockMultipartFile photo = new MockMultipartFile("photo", "profile.jpg", MediaType.IMAGE_JPEG_VALUE,
                "image-data".getBytes());

        UserResponseDTO responseDTO = UserResponseDTO.builder()
                .id(UUID.randomUUID().toString())
                .regId("REG00000001")
                .fullName("Jatin Kumar")
                .gender("Male")
                .dob("01/01/2000")
                .email("jatin@example.com")
                .mobile("9876543210")
                .state("Delhi")
                .city("New Delhi")
                .build();

        Mockito.when(userService.register(Mockito.any())).thenReturn(responseDTO);

        mockMvc.perform(multipart("/api/users")
                .file(photo)
                .param("fullName", "Jatin Kumar")
                .param("gender", "Male")
                .param("dob", "01/01/2000")
                .param("email", "jatin@example.com")
                .param("mobile", "9876543210")
                .param("state", "Delhi")
                .param("city", "New Delhi")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.fullName", is("Jatin Kumar")))
                .andExpect(jsonPath("$.data.regId", notNullValue()));
    }

    @Test
    void testGetUsers() throws Exception {
        List<UserResponseDTO> userList = List.of(
                UserResponseDTO.builder().id(UUID.randomUUID().toString()).fullName("Jatin").regId("REG00000001")
                        .build(),
                UserResponseDTO.builder().id(UUID.randomUUID().toString()).fullName("Ankit").regId("REG00000002")
                        .build());

        Mockito.when(userService.getUsers(null, null, null)).thenReturn(userList);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(2)))
                .andExpect(jsonPath("$.data[0].regId", is("REG00000001")));
    }
}
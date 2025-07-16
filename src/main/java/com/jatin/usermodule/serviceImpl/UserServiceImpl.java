package com.jatin.usermodule.serviceImpl;

import com.jatin.usermodule.dto.UserRequestDTO;
import com.jatin.usermodule.dto.UserResponseDTO;
import com.jatin.usermodule.entity.User;
import com.jatin.usermodule.repository.UserRepository;
import com.jatin.usermodule.service.UserService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Value("${upload.dir:uploads}")
    private String uploadDir;

    @Value("${server.base-url:http://localhost:8080}")
    private String baseUrl;

    @Override
    public UserResponseDTO register(UserRequestDTO dto) {
        if (dto.getEmail() != null && userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        // Validate: either mobile or phone must be present
        if ((dto.getMobile() == null || dto.getMobile().isBlank()) &&
                (dto.getPhone() == null || dto.getPhone().isBlank())) {
            throw new IllegalArgumentException("Either mobile or phone must be provided");
        }

        String hobbiesStr = dto.getHobbies() != null ? String.join(",", dto.getHobbies()) : null;

        String photoFileName = null;

        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            MultipartFile file = dto.getPhoto();
            String originalName = file.getOriginalFilename();

            if (originalName == null ||
                    !(originalName.toLowerCase().endsWith(".jpg") || originalName.toLowerCase().endsWith(".png"))) {
                throw new IllegalArgumentException("Only JPG and PNG files are allowed");
            }

            photoFileName = System.currentTimeMillis() + "_" + originalName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");

            try {
                Path uploadPath = Paths.get(System.getProperty("user.dir"), uploadDir);
                Files.createDirectories(uploadPath);

                Path target = uploadPath.resolve(photoFileName);
                file.transferTo(target.toFile());

            } catch (IOException e) {
                throw new RuntimeException("Failed to upload photo", e);
            }
        }

        // Save user first (without regId)
        User user = User.builder()
                .fullName(dto.getFullName())
                .gender(dto.getGender())
                .dob(dto.getDob())
                .email(dto.getEmail())
                .mobile(dto.getMobile())
                .phone(dto.getPhone())
                .state(dto.getState())
                .city(dto.getCity())
                .hobbies(hobbiesStr)
                .photoUrl(photoFileName)
                .build();

        String regId = "REG" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
        user.setRegId(regId);
        user = userRepository.save(user);

        return mapToResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getUsers(String name, String gender, String state) {
        List<User> all = userRepository.findAll();

        return all.stream()
                .filter(u -> name == null || u.getFullName().toLowerCase().contains(name.toLowerCase()))
                .filter(u -> gender == null || u.getGender().equalsIgnoreCase(gender))
                .filter(u -> state == null || u.getState().equalsIgnoreCase(state))
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        UserResponseDTO dto = modelMapper.map(user, UserResponseDTO.class);
        if (user.getPhotoUrl() != null) {
            dto.setPhotoUrl(baseUrl + "/uploads/" + user.getPhotoUrl());
        }
        return dto;
    }
}

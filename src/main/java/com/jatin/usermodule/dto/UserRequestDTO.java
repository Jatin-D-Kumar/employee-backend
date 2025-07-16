package com.jatin.usermodule.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

        @NotBlank(message = "Full name is required")
        @Size(max = 25, message = "Name must not exceed 25 characters")
        private String fullName;

        @NotBlank(message = "Gender is required")
        private String gender;

        @NotBlank(message = "Date of birth is required")
        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "DOB must be in dd/mm/yyyy format")
        private String dob;

        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        private String email;

        private String mobile; // Validated in service: mobile OR phone must be present

        private String phone;

        @NotBlank(message = "State is required")
        private String state;

        @NotBlank(message = "City is required")
        private String city;

        private String[] hobbies; // Optional, converted to comma-separated string

        private MultipartFile photo; // Optional, validated in service layer
}

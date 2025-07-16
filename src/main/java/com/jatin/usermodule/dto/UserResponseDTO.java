package com.jatin.usermodule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {

    private String id;

    private String regId;

    private String fullName;

    private String gender;

    private String dob;

    private String email;

    private String mobile;

    private String phone;

    private String state;

    private String city;

    private String[] hobbies;

    private String photoUrl;
}

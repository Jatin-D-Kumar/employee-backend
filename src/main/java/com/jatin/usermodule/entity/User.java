package com.jatin.usermodule.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(nullable = false, length = 25)
    private String fullName;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(nullable = false, length = 10)
    private String dob;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 15)
    private String mobile;

    @Column(length = 15)
    private String phone;

    @Column(nullable = false, length = 50)
    private String state;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(length = 100)
    private String hobbies;

    private String photoUrl;

    @Column(unique = true, length = 20)
    private String regId;
}

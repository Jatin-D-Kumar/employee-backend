package com.jatin.usermodule.repository;

import com.jatin.usermodule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByGenderIgnoreCase(String gender);

    List<User> findByStateIgnoreCase(String state);

    List<User> findByFullNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);

}

package com.company.repository;


import com.company.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByUsername(String username);


    List<User> findByUsernameNot(String username);


    Optional<User> findById(Long id);
}

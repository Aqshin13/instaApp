package com.company.service;

import com.company.dto.UserRegisterDTO;
import com.company.entities.User;
import com.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void save(UserRegisterDTO dto){
        User user= User.builder()
                .password(passwordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .fullName(dto.getFullName())
                .build();

        userRepository.save(user);

    }


    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }


}

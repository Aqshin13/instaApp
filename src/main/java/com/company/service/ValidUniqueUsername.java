package com.company.service;

import com.company.entities.User;
import com.company.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


public class ValidUniqueUsername implements ConstraintValidator<UniqueUsername,String> {


    @Autowired
    public UserRepository userRepository;


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> inDB = userRepository.findByUsername(s);
        return inDB.isEmpty();
    }
}

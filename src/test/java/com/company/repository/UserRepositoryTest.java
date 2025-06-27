package com.company.repository;

import com.company.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenUsernameWhenFindByUsernameThenReturnOptionalUser() {
//        given
        String username = "Agshin";
        User user = User.builder()
                .username(username)
                .password("P+4ssword")
                .fullName("Agshin Shadow")
                .build();
        userRepository.save(user);
//        when
        Optional<User> userDB = userRepository.findByUsername(username);
//       then
        assertTrue(userDB.isPresent());
        assertEquals(username, userDB.get().getUsername());
    }


    @Test
    public void givenUsernameWhenFindByUsernameNotThenReturnList() {
//       given
        String username = "Agshin";
        User user1 = User.builder()
                .username(username)
                .password("P+4ssword")
                .fullName("Agshin Shadow")
                .build();
        User user2 = User.builder()
                .username("Agshin1")
                .password("P+4ssword")
                .fullName("Agshin Shadow")
                .build();
        User user3 = User.builder()
                .username("Agshin2")
                .password("P+4ssword")
                .fullName("Agshin Shadow")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
//        when
        List<User> users = userRepository.findByUsernameNot(username);
//       then
        assertFalse(users.isEmpty());
        assertEquals(2, users.size());
    }


    @Test
    public void givenIdWhenFindByIdThenReturnOptionalUser() {
        String username = "Agshin";
        User user = User.builder()
                .username(username)
                .password("P+4ssword")
                .fullName("Agshin Shadow")
                .build();
        User id=  userRepository.save(user);
        Optional<User> optional = userRepository.findById(id.getId());
        System.out.println(userRepository.findAll().size()+" ++++++++++++++");
        assertTrue(optional.isPresent());
        assertEquals(username, optional.get().getUsername());
    }

}
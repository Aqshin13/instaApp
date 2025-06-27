package com.company.service;

import com.company.dto.UserRegisterDTO;
import com.company.entities.User;
import com.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private static  String UPLOAD_DIR = "./images/profile/";


    public void save(UserRegisterDTO dto) {
        User user = User.builder()
                .password(passwordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .fullName(dto.getFullName())
                .build();
        userRepository.save(user);
    }


    public void saveProfileImage(User user, MultipartFile file) throws Exception {
        String oldImage = user.getImageUrl();
        String imageName = UUID.randomUUID().toString();
        String filePath = UPLOAD_DIR + imageName;
        File destinationFile = new File(filePath);
        byte[] fileData = file.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
        fileOutputStream.write(fileData);
        fileOutputStream.close();
        user.setImageUrl(imageName);
        userRepository.save(user);
        if (oldImage != null)
            Files.delete(Path.of(UPLOAD_DIR, oldImage));
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    public List<User> findByUsernameNot(String username) {
        return userRepository.findByUsernameNot(username);
    }


    public void updateUser(String name, String fullName, User user) {
        if (!name.isEmpty()) {
            user.setUsername(name);
        }
        if (!fullName.isEmpty()) {
            user.setFullName(fullName);
        }
        userRepository.save(user);
        UserDetails updatedUserDetails =
                userDetailService.loadUserByUsername(user.getUsername());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        updatedUserDetails, updatedUserDetails.getPassword(), updatedUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}

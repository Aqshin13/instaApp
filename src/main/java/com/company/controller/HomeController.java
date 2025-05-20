package com.company.controller;

import com.company.dto.UserListDTO;
import com.company.entities.Follows;
import com.company.entities.Shares;
import com.company.entities.User;
import com.company.repository.FollowRepository;
import com.company.repository.SharesRepository;
import com.company.repository.UserRepository;
import com.company.service.FollowService;
import com.company.service.UserDetailServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Controller
public class HomeController {


    private static final String UPLOAD_DIR = "./images/uploads/";


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private SharesRepository sharesRepository;

    @Autowired
    private FollowService followService;

    @GetMapping("/home")
    public String getHome(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<User> users = userRepository.findByUsernameNot(userDetails.getUsername());
        User loggedInUser = userRepository.findByUsername(userDetails.getUsername()).get();
        List<Follows> following = followRepository.findByFollower(loggedInUser);
        List<Follows> followsReuqest = followService.getFollowRequest(loggedInUser);
        List<List<Shares>> images = new ArrayList<>();
        for (Follows follows : following) {
            images.add(follows.getFollowing().getShares());
        }
        images.add(loggedInUser.getShares());
        Map<Long, String> followStatusMap = new HashMap<>();
        for (User user : users) {
            Follows.FollowStatus status = followService.getFollowStatus(loggedInUser, user);
            followStatusMap.put(user.getId(), status.name());  // ID -> Status eşlemesi
        }
        List<UserListDTO> list = users.stream().map(UserListDTO::new).toList();
        model.addAttribute("requests", followsReuqest);
        model.addAttribute("requestCount", followsReuqest.size());
        model.addAttribute("users", list);
        model.addAttribute("followStatusMap", followStatusMap);
        model.addAttribute("images", images);
        model.addAttribute("user", loggedInUser);
        return "home";
    }

    @SneakyThrows
    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("file") MultipartFile file,
                              @AuthenticationPrincipal UserDetails user) {
        if (file.isEmpty()) {
            return "redirect:/home?error=File is empty";
        }
        User userId = userRepository.findByUsername(user.getUsername()).get();
        Shares shares = new Shares();
        try {
            String imageName = UUID.randomUUID().toString();
            String filePath = UPLOAD_DIR + imageName;
            File destinationFile = new File(filePath);
            byte[] fileData = file.getBytes();
            FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
            fileOutputStream.write(fileData);
            fileOutputStream.close();
            shares.setImageName(imageName);
            shares.setUser(userId);
            sharesRepository.save(shares);
            return "redirect:/home";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/home?error=File upload failed";
        }
    }


    @SneakyThrows
    @PostMapping("/delete")
    public String deletePost(@RequestParam Long sharesId) {
       Shares shares= sharesRepository.findById(sharesId).get();
        if (sharesId != null){
            sharesRepository.deleteById(sharesId);
        }
        Files.delete(Path.of(UPLOAD_DIR, shares.getImageName()));
        return "redirect:/home";
    }


}

package com.company.controller;

import com.company.dto.UserListDTO;
import com.company.entities.Follows;
import com.company.entities.Shares;
import com.company.entities.User;
import com.company.repository.FollowRepository;
import com.company.repository.SharesRepository;
import com.company.repository.UserRepository;
import com.company.service.FollowService;
import com.company.service.ShareService;
import com.company.service.UserDetailServiceImpl;
import com.company.service.UserService;
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



    @Autowired
    private ShareService shareService;

    @Autowired
    private FollowService followService;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String getHome(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<User> users = userService.findByUsernameNot(userDetails.getUsername());
        User loggedInUser = userService.findByUsername(userDetails.getUsername());
        List<Follows> following = followService.findByFollower(loggedInUser);
        List<Follows> followsReuqest = followService.getFollowRequest(loggedInUser);
        List<List<Shares>> images = new ArrayList<>();
        for (Follows follows : following) {
            images.add(follows.getFollowing().getShares());
        }
        images.add(loggedInUser.getShares());
        Map<Long, String> followStatusMap = new HashMap<>();
        for (User user : users) {
            Follows.FollowStatus status = followService.getFollowStatus(loggedInUser, user);
            followStatusMap.put(user.getId(), status.name());
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
        User userId = userService.findByUsername(user.getUsername());
        try {
            shareService.save(userId,file);
            return "redirect:/home";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/home?error=File upload failed";
        }
    }


    @PostMapping("/delete")
    public String deletePost(@RequestParam Long sharesId) {
        shareService.deleteById(sharesId);
        return "redirect:/home";
    }


}

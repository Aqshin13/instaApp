package com.company.controller;

import com.company.entities.Follows;
import com.company.entities.Shares;
import com.company.entities.User;
import com.company.repository.FollowRepository;
import com.company.repository.SharesRepository;
import com.company.repository.UserRepository;
import com.company.service.FollowService;
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
import java.util.List;
import java.util.UUID;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;



    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<Follows> followsRequest=followService.getFollowRequest(user);
        model.addAttribute("user", user);
        model.addAttribute("requests",followsRequest);
        model.addAttribute("requestCount",followsRequest.size());
        System.out.println(user.getFollowing().size());
        System.out.println(user.getFollower().size());
        System.out.println(user.getShares().size());
        return "profile";
    }


    @SneakyThrows
    @PostMapping("/profileImage")
    public String changeProfileImage(@RequestParam("file") MultipartFile file,
                                     @AuthenticationPrincipal UserDetails user) {
        if (file.isEmpty()) {
            return "redirect:/profile?error=File is empty";
        }
        User userId = userService.findByUsername(user.getUsername());
        try{
            userService.saveProfileImage(userId, file);
            return "redirect:/profile";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/profile?error=File upload failed";
        }
    }


}

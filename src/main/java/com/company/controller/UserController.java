package com.company.controller;


import com.company.entities.User;
import com.company.repository.UserRepository;
import com.company.service.UserDetailServiceImpl;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/edit")
    public String edit(@AuthenticationPrincipal UserDetails userDetails,
                       @RequestParam("name") String name,
                       @RequestParam("fullName") String fullName) {

        User user = userService.findByUsername(userDetails.getUsername());
        userService.updateUser(name, fullName, user);
        return "redirect:/profile";
    }


}

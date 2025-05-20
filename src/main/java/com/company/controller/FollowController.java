package com.company.controller;


import com.company.entities.Follows;
import com.company.entities.User;
import com.company.repository.FollowRepository;
import com.company.service.FollowService;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class FollowController {


    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;


    @PostMapping("/follow")
    public String followUser(@RequestParam Long followingId,
                             @AuthenticationPrincipal UserDetails currentUser) {
        User follower = userService.findByUsername(currentUser.getUsername());
        User following = userService.findById(followingId);
        followService.sendFollowRequest(follower, following);
        return "redirect:/home";
    }

    @PostMapping("/unfollow")
    public String unfollowUser(@RequestParam Long followingId, @AuthenticationPrincipal UserDetails currentUser) {
        User follower = userService.findByUsername(currentUser.getUsername());
        User following = userService.findById(followingId);
        followService.removeFollow(follower, following);
        return "redirect:/home";
    }


    @PostMapping("/acceptRequest")
    public String acceptRequest(@RequestParam (name = "followIdq")String followId) {
       Follows follows=followService.findById(Long.valueOf(followId));
       follows.setStatus(Follows.FollowStatus.ACCEPTED);
        followService.save(follows);
        return "redirect:/home";
    }


    @PostMapping("/rejectRequest")
    public String rejectRequest(@RequestParam(name="followId") String followId) {
        followService.delete(Long.valueOf(followId));
        return "redirect:/home";
    }

}

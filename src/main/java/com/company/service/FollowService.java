package com.company.service;


import com.company.entities.Follows;
import com.company.entities.User;
import com.company.repository.FollowRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    public Follows.FollowStatus getFollowStatus(User follower,
                                                User following) {
        Optional<Follows> follow = followRepository
                .findByFollowerAndFollowing(follower, following);
        return follow.map(Follows::getStatus)
                .orElse(Follows.FollowStatus.NONE);
    }


    public void sendFollowRequest(User follower, User following) {
        if (!followRepository.existsByFollowerAndFollowing(follower, following)) {
            Follows follow = new Follows();
            follow.setFollower(follower);
            follow.setFollowing(following);
            follow.setStatus(Follows.FollowStatus.PENDING);
            followRepository.save(follow);
        }
    }


    public void removeFollow(User follower, User following) {
        followRepository.deleteByFollowerAndFollowing(follower, following);
    }


    public List<Follows> getFollowRequest(User user){
        return followRepository
                .findByFollowingAndStatus(user, Follows.FollowStatus.PENDING);
    }


    public Follows findById(Long id){
        return followRepository.findById(id).orElse(null);
    }


    public  void save(Follows follows){
        followRepository.save(follows);
    }


    public void delete(Long id){
        followRepository.deleteById(id);
    }


    public  List<Follows> findByFollower(User user){
        return followRepository.findByFollower(user);
    }

}

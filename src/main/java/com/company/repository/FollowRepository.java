package com.company.repository;

import com.company.entities.Follows;
import com.company.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follows,Long> {

    List<Follows> findByFollower(User user);


    Optional<Follows> findByFollowerAndFollowing(User follower,User following);


    Boolean existsByFollowerAndFollowing(User follower,User following);

    void deleteByFollowerAndFollowing(User follower, User following);


    List<Follows> findByFollowingAndStatus(User following,Follows.FollowStatus status);

    List<Follows> findByFollowerAndStatus(User follower, Follows.FollowStatus status);


}

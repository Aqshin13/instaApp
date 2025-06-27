package com.company.repository;

import com.company.entities.Follows;
import com.company.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class FollowRepositoryTest {


    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    private User follower;

    private User following;

    @BeforeEach
    public void setup() {
        follower = User.builder().username("follower").build();
        following = User.builder().username("following").build();
        userRepository.saveAndFlush(follower);
        userRepository.saveAndFlush(following);
        followRepository.saveAndFlush(Follows.builder()
                .follower(follower)
                .following(following)
                .status(Follows.FollowStatus.ACCEPTED)
                .build());
    }

    @Test
    public void givenUserWhenFindByFollowerThenReturnListFollow() {
        List<Follows> follows = followRepository.findByFollower(follower);
        assertNotNull(follows);
        assertEquals(1, follows.size());
        assertEquals(following.getUsername(), follows.get(0).getFollowing().getUsername());
    }


    @Test
    public void givenFollowerAndFollowingWhenFindByFollowerAndFollowingThenReturnOptionalFollows() {
      Optional<Follows> follows=
              followRepository.findByFollowerAndFollowing(follower,following);
      assertTrue(follows.isPresent());
    }

    @Test
    public void givenFollowerAndFollowingWhenExistsByFollowerAndFollowingThenReturnBoolean() {
       Boolean isExist= followRepository.existsByFollowerAndFollowing(follower,following);
       assertTrue(isExist);
    }

}
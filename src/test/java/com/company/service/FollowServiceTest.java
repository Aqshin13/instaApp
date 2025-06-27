package com.company.service;

import com.company.entities.Follows;
import com.company.entities.User;
import com.company.repository.FollowRepository;
import com.company.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {


    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private FollowService followService;


    private User follower;

    private User following;

    @BeforeEach
    void setUp() {
        follower = User.builder().username("follower").build();
        following = User.builder().username("following").build();
    }


    @Test
    public void givenFollowerAndFollowingWhenGetFollowStatusThenReturnFollowStatus() {

        given(followRepository.findByFollowerAndFollowing(follower, following))
                .willReturn(Optional.of(Follows.builder()
                        .following(following)
                        .follower(follower)
                        .status(Follows.FollowStatus.PENDING)
                        .build()));
        Follows.FollowStatus status = followService.getFollowStatus(follower, following);
        assertEquals(Follows.FollowStatus.PENDING, status);
        verify(followRepository, times(1))
                .findByFollowerAndFollowing(follower, following);

    }


    @Test
    void shouldRemoveExistingFollow() {
        followService.removeFollow(follower, following);
        verify(followRepository, times(1))
                .deleteByFollowerAndFollowing(follower, following);
    }

}
package com.company.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "follows")
public class Follows extends BaseEntity {


    @ManyToOne
    private User follower;
    @ManyToOne
    private User following;
    @Enumerated(EnumType.STRING)
    private FollowStatus status; //


    public enum FollowStatus {
        PENDING,
        ACCEPTED,
        NONE
    }

}

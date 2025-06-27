package com.company.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "follows")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

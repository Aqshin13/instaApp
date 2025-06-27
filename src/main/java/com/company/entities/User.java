package com.company.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User  extends BaseEntity{
    private String username;
    private String password;
    private String fullName;
    private String imageUrl;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Shares> shares;
    @OneToMany(mappedBy = "follower",fetch = FetchType.EAGER)
    private List<Follows> follower;
    @OneToMany(mappedBy = "following",fetch = FetchType.EAGER)
    private List<Follows> following;
}

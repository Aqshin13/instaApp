package com.company.dto;


import com.company.entities.Follows;
import com.company.entities.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserListDTO {

    private long id;
    private String username;
    private String image;
    private Follows.FollowStatus status;

    public UserListDTO(User user){
        setId(user.getId());
        setUsername(user.getUsername());
        setImage(user.getImageUrl());

    }
}

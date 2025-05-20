package com.company.dto;

import com.company.service.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRegisterDTO {

    @NotBlank
    @Size(min = 4, max = 8)
    @UniqueUsername
    String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Password get to include number character and letter")
    String password;
    @NotBlank
    @Size(min = 4, max = 8)
    String fullName;

}




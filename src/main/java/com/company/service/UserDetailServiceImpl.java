package com.company.service;


import com.company.entities.User;
import com.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> inDB = userRepository.findByUsername(username);
        if(inDB.isEmpty()) {
            throw new UsernameNotFoundException(username + " is not found");
        }
        return new UserDetails() {


            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return AuthorityUtils.createAuthorityList("ADMIN");
            }

            @Override
            public String getPassword() {
                return inDB.get().getPassword();
            }

            @Override
            public String getUsername() {
                return inDB.get().getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}

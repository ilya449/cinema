package com.cinema.security;

import com.cinema.model.User;
import com.cinema.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        org.springframework.security.core.userdetails.User.UserBuilder userBuilder
                = org.springframework.security.core.userdetails.User.withUsername(email);
        userBuilder.password(user.getPassword());
        userBuilder.roles(user.getRoles().stream()
                .map(r -> r.getRoleName().toString())
                .toArray(String[]::new));
        return userBuilder.build();
    }
}

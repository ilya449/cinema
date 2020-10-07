package com.cinema.service.impl;

import com.cinema.exception.AuthenticationException;
import com.cinema.lib.Inject;
import com.cinema.lib.Service;
import com.cinema.model.User;
import com.cinema.service.AuthenticationService;
import com.cinema.service.UserService;
import com.cinema.util.HashUtil;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && isPassValid(password, user.get())) {
            return user.get();
        }
        throw new AuthenticationException("Incorrect user email or password");
    }

    @Override
    public User register(String email, String password) {
        return userService.add(new User(email, password));
    }

    private boolean isPassValid(String password, User user) {
        return HashUtil.hashPassword(password, user.getSalt())
                .equals(user.getPassword());
    }
}

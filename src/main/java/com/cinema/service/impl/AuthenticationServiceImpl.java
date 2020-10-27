package com.cinema.service.impl;

import com.cinema.exception.AuthenticationException;
import com.cinema.model.User;
import com.cinema.service.AuthenticationService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import com.cinema.util.HashUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public AuthenticationServiceImpl(UserService userService,
                                     ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email);
        if (user != null && isPassValid(password, user)) {
            log.info("User logged: " + user);
            return user;
        }
        throw new AuthenticationException("Incorrect user email or password");
    }

    @Override
    public User register(String email, String password) {
        User user = new User(email, password);
        shoppingCartService.registerNewShoppingCart(userService.add(user));
        log.info("New user was registered: " + user);
        return user;
    }

    private boolean isPassValid(String password, User user) {
        return HashUtil.hashPassword(password, user.getSalt())
                .equals(user.getPassword());
    }
}

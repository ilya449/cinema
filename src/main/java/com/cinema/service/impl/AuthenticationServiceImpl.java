package com.cinema.service.impl;

import com.cinema.model.User;
import com.cinema.service.AuthenticationService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
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
    public User register(String email, String password) {
        User user = new User(email, password);
        shoppingCartService.registerNewShoppingCart(userService.add(user));
        log.info("New user was registered: " + user);
        return user;
    }
}

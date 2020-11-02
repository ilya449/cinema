package com.cinema.service;

import com.cinema.model.User;

public interface AuthenticationService {
    User register(String email, String password);
}

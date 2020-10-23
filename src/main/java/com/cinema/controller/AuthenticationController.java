package com.cinema.controller;

import com.cinema.model.dto.UserRegistrationRequestDto;
import com.cinema.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registration")
    public void registerUser(@RequestBody UserRegistrationRequestDto dto) {
        authenticationService.register(dto.getEmail(), dto.getPassword());
    }
}

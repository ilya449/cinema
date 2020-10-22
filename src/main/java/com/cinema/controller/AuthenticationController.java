package com.cinema.controller;

import com.cinema.exception.AuthenticationException;
import com.cinema.model.dto.UserRegistrationRequestDto;
import com.cinema.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registration")
    private RedirectView registerUser(@RequestBody UserRegistrationRequestDto dto){
        authenticationService.register(dto.getEmail(), dto.getPassword());
        return new RedirectView("/hello");
    }

    @PostMapping("/registration")
    private RedirectView authenticateUser(@RequestBody UserRegistrationRequestDto dto)
            throws AuthenticationException {
        authenticationService.login(dto.getEmail(), dto.getPassword());
        return new RedirectView("/hello");
    }
}

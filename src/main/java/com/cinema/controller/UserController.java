package com.cinema.controller;

import com.cinema.model.dto.user.UserResponseDto;
import com.cinema.service.UserService;
import com.cinema.service.mapper.UserDtoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserDtoMapper mapper;

    public UserController(UserService userService, UserDtoMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/by-email")
    public UserResponseDto getUserByEmail(@RequestParam String email) {
        return mapper.getUserResponseDto(userService.findByEmail(email));
    }
}

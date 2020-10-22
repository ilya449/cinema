package com.cinema.model.dto;

import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    private String email;
    private String password;
}

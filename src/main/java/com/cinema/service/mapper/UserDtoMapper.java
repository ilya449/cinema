package com.cinema.service.mapper;

import com.cinema.model.User;
import com.cinema.model.dto.user.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public UserResponseDto getUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setEmail(user.getEmail());
        dto.setId(user.getId());
        return dto;
    }
}

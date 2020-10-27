package com.cinema.service.mapper;

import com.cinema.model.User;
import com.cinema.model.dto.user.UserResponseDto;
import com.cinema.service.MovieService;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    private final MovieService movieService;

    public UserDtoMapper(MovieService movieService) {
        this.movieService = movieService;
    }

    public UserResponseDto getUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setEmail(user.getEmail());
        dto.setId(user.getId());
        return dto;
    }
}

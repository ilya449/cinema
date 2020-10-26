package com.cinema.model.dto.session;

import lombok.Data;

@Data
public class MovieSessionRequestDto {
    private Long movieId;
    private Long cinemaHallId;
    private String showTime;
}

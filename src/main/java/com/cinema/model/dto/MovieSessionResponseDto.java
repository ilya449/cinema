package com.cinema.model.dto;

import lombok.Data;

@Data
public class MovieSessionResponseDto {
    private Long movieId;
    private Long cinemaHallId;
    private String movieTitle;
    private String showTime;
}

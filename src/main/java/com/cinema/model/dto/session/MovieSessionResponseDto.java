package com.cinema.model.dto.session;

import lombok.Data;

@Data
public class MovieSessionResponseDto {
    private Long movieId;
    private Long movieSessionId;
    private Long cinemaHallId;
    private String movieTitle;
    private String showTime;
}

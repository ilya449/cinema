package com.cinema.model.dto.hall;

import lombok.Data;

@Data
public class CinemaHallResponseDto {
    private Long id;
    private Integer capacity;
    private String description;
}

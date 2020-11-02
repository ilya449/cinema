package com.cinema.model.dto.hall;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CinemaHallRequestDto {
    @NotNull
    private Integer capacity;
    @NotNull
    private String description;
}

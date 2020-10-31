package com.cinema.controller;

import com.cinema.model.dto.hall.CinemaHallRequestDto;
import com.cinema.model.dto.hall.CinemaHallResponseDto;
import com.cinema.service.CinemaHallService;
import com.cinema.service.mapper.CinemaHallDtoMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinema-hall")
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;
    private final CinemaHallDtoMapper mapper;

    public CinemaHallController(CinemaHallService cinemaHallService, CinemaHallDtoMapper mapper) {
        this.cinemaHallService = cinemaHallService;
        this.mapper = mapper;
    }

    @PostMapping
    public void addCinemaHall(@RequestBody CinemaHallRequestDto dto) {
        cinemaHallService.add(mapper.getCinemaHall(dto));
    }

    @GetMapping("/all")
    public List<CinemaHallResponseDto> getAllCinemaHalls() {
        return cinemaHallService.getAll().stream()
                .map(mapper::getCinemaHallResponseDto)
                .collect(Collectors.toList());
    }
}

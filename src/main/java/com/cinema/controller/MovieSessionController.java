package com.cinema.controller;

import com.cinema.model.dto.session.MovieSessionRequestDto;
import com.cinema.model.dto.session.MovieSessionResponseDto;
import com.cinema.service.MovieSessionService;
import com.cinema.service.mapper.MovieSessionDtoMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-sessions")
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final MovieSessionDtoMapper mapper;

    public MovieSessionController(MovieSessionService movieSessionService,
                                  MovieSessionDtoMapper mapper) {
        this.movieSessionService = movieSessionService;
        this.mapper = mapper;
    }

    @PostMapping
    public void addMovieSession(@RequestBody @Valid MovieSessionRequestDto dto) {
        movieSessionService.add(mapper.getMovieSession(dto));
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> getAllMovieSessions(@RequestParam Long id,
                                                             @RequestParam
                                                             @DateTimeFormat(pattern
                                                                     = "dd.MM.yyyy")
                                                                     LocalDate date) {
        return movieSessionService.findAvailableSessions(id, date).stream()
                .map(mapper::getMovieSessionResponseDto)
                .collect(Collectors.toList());
    }
}

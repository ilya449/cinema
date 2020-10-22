package com.cinema.controller;

import com.cinema.model.dto.session.MovieSessionDtoMapper;
import com.cinema.model.dto.session.MovieSessionRequestDto;
import com.cinema.model.dto.session.MovieSessionResponseDto;
import com.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

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
    public RedirectView addMovieSession(@RequestBody MovieSessionRequestDto dto) {
        movieSessionService.add(mapper.getMovieSession(dto));
        return new RedirectView("/");
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> getAllMovieSessions(Long id, String date) {
        return movieSessionService.findAvailableSessions(id, LocalDate.parse(date)).stream()
                .map(mapper::getMovieSessionResponseDto)
                .collect(Collectors.toList());
    }
}

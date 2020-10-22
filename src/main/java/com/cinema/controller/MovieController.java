package com.cinema.controller;

import com.cinema.model.dto.movie.MovieDtoMapper;
import com.cinema.model.dto.movie.MovieRequestDto;
import com.cinema.model.dto.movie.MovieResponseDto;
import com.cinema.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieDtoMapper mapper;

    public MovieController(MovieService movieService, MovieDtoMapper mapper) {
        this.movieService = movieService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MovieResponseDto> getAllMovies() {
        return movieService.getAll().stream()
                .map(mapper::getMovieResponseDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public RedirectView addMovie(@RequestBody MovieRequestDto dto) {
        movieService.add(mapper.getMovie(dto));
        return new RedirectView("/movies");
    }
}

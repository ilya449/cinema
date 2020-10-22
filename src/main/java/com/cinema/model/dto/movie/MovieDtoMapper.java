package com.cinema.model.dto.movie;

import com.cinema.model.Movie;
import com.cinema.service.MovieService;
import org.springframework.stereotype.Component;

@Component
public class MovieDtoMapper {
    private final MovieService movieService;

    public MovieDtoMapper(MovieService movieService) {
        this.movieService = movieService;
    }

    public Movie getMovie(MovieRequestDto dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        return movie;
    }

    public MovieResponseDto getMovieResponseDto(Movie movie) {
        MovieResponseDto dto = new MovieResponseDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        return dto;
    }
}

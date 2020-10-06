package com.cinema.service.impl;

import com.cinema.model.MovieSession;
import com.cinema.service.MovieSessionService;

import java.time.LocalDate;
import java.util.List;

public class MovieSessionServiceImpl implements MovieSessionService {
    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return null;
    }

    @Override
    public MovieSession add(MovieSession session) {
        return null;
    }
}

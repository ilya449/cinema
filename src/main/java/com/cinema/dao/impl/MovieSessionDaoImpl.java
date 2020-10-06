package com.cinema.dao.impl;

import com.cinema.dao.MovieSessionDao;
import com.cinema.lib.Dao;
import com.cinema.model.MovieSession;
import java.time.LocalDate;
import java.util.List;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public List<MovieSession> findSessions(Long movieId, LocalDate date) {
        return null;
    }

    @Override
    public MovieSession add(MovieSession session) {
        return null;
    }
}

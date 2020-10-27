package com.cinema.dao.impl;

import com.cinema.dao.MovieDao;
import com.cinema.exception.DataProcessingException;
import com.cinema.model.Movie;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@Log4j
public class MovieDaoImpl implements MovieDao {
    private final SessionFactory sessionFactory;

    public MovieDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Movie add(Movie movie) {
        Transaction transaction = null;
        Session session = null;
        log.info("Creating movie: " + movie);
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(movie);
            transaction.commit();
            log.info("Movie was created: " + movie);
            return movie;
        } catch (Exception e) {
            log.error("Can't create movie: " + movie + "\nerror: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cant insert movie entity: " + movie, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Movie> getAllMoviesQuery = session.createQuery("from Movie", Movie.class);
            return getAllMoviesQuery.getResultList();
        }
    }

    @Override
    public Movie get(Long id) {
        log.info("Trying to get Movie with id: " + id);
        try (Session session = sessionFactory.openSession()) {
            return session.get(Movie.class, id);
        }
    }
}

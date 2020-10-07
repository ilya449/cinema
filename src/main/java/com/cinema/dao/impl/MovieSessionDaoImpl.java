package com.cinema.dao.impl;

import com.cinema.dao.MovieSessionDao;
import com.cinema.exception.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.MovieSession;
import com.cinema.util.HibernateUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery("FROM MovieSession "
                            + "WHERE movie_id = :movieId "
                            + "AND show_time BETWEEN :timeFrom AND :timeTo",
                    MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("timeFrom", date.atStartOfDay());
            query.setParameter("timeTo", date.atTime(LocalTime.MAX));
            return query.getResultList();
        }
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cant add movie session: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}

package com.cinema.dao.impl;

import com.cinema.Main;
import com.cinema.dao.MovieSessionDao;
import com.cinema.exception.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.MovieSession;
import com.cinema.util.HibernateUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Logger logger = Logger.getLogger(Main.class);

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        logger.info("Creating movie session: " + movieSession);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            logger.info("Movie session was created: " + movieSession);
            return movieSession;
        } catch (Exception e) {
            logger.error("Can't create movie session: " + movieSession + "\nerror: ", e);
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
}

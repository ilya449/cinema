package com.cinema.dao.impl;

import com.cinema.dao.CinemaHallDao;
import com.cinema.exception.DataProcessingException;
import com.cinema.model.CinemaHall;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@Log4j
public class CinemaHallDaoImpl implements CinemaHallDao {
    private final SessionFactory sessionFactory;

    public CinemaHallDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        Session session = null;
        log.info("Creating cinema hall: " + cinemaHall);
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            log.info("Cinema hall was created: " + cinemaHall);
            return cinemaHall;
        } catch (Exception e) {
            log.error("Can't create cinema hall: " + cinemaHall + "\nerror: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cant add cinema hall: " + cinemaHall, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<CinemaHall> cinemaHallQuery
                    = session.createQuery("from CinemaHall", CinemaHall.class);
            return cinemaHallQuery.getResultList();
        }
    }

    @Override
    public CinemaHall get(Long id) {
        log.info("Trying to get Cinema Hall with id: " + id);
        try (Session session = sessionFactory.openSession()) {
            return session.get(CinemaHall.class, id);
        }
    }
}

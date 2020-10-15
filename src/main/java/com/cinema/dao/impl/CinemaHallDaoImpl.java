package com.cinema.dao.impl;

import com.cinema.Main;
import com.cinema.dao.CinemaHallDao;
import com.cinema.exception.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.CinemaHall;
import com.cinema.util.HibernateUtil;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private static final Logger logger = Logger.getLogger(Main.class);

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        Session session = null;
        logger.info("Creating cinema hall: " + cinemaHall);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            logger.info("Cinema hall was created: " + cinemaHall);
            return cinemaHall;
        } catch (Exception e) {
            logger.error("Can't create cinema hall: " + cinemaHall + "\nerror: ", e);
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CinemaHall> cinemaHallQuery
                    = session.createQuery("from CinemaHall", CinemaHall.class);
            return cinemaHallQuery.getResultList();
        }
    }
}

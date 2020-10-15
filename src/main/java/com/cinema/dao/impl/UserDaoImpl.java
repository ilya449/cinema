package com.cinema.dao.impl;

import com.cinema.Main;
import com.cinema.dao.UserDao;
import com.cinema.exception.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.User;
import com.cinema.util.HibernateUtil;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class UserDaoImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public User add(User user) {
        Transaction transaction = null;
        Session session = null;
        logger.info("Creating user: " + user);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            logger.info("User was created: " + user);
            return user;
        } catch (Exception e) {
            logger.error("Can't create " + user + ", \nerror:", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cant add user: " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User "
                    + "WHERE email = :email ", User.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        }
    }
}

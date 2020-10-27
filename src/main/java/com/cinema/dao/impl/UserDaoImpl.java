package com.cinema.dao.impl;

import com.cinema.dao.UserDao;
import com.cinema.exception.DataProcessingException;
import com.cinema.model.User;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User add(User user) {
        Transaction transaction = null;
        Session session = null;
        logger.info("Creating user: " + user);
        try {
            session = sessionFactory.openSession();
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
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User "
                    + "WHERE email = :email ", User.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public User get(Long id) {
        logger.info("Trying to get User with id: " + id);
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }
}

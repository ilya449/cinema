package com.cinema.dao.impl;

import com.cinema.dao.UserDao;
import com.cinema.exception.DataProcessingException;
import com.cinema.model.User;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@Log4j
public class UserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User add(User user) {
        Transaction transaction = null;
        Session session = null;
        log.info("Creating user: " + user);
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            log.info("User was created: " + user);
            return user;
        } catch (Exception e) {
            log.error("Can't create " + user + ", \nerror:", e);
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
    public User findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User JOIN FETCH User.roles "
                    + "WHERE User.email = :email ", User.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        }
    }

    @Override
    public User get(Long id) {
        log.info("Trying to get User with id: " + id);
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }
}

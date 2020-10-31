package com.cinema.dao.impl;

import com.cinema.dao.ShoppingCartDao;
import com.cinema.exception.DataProcessingException;
import com.cinema.model.ShoppingCart;
import com.cinema.model.User;
import lombok.extern.log4j.Log4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@Log4j
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    private final SessionFactory sessionFactory;

    public ShoppingCartDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        Session session = null;
        log.info("Creating shopping cart: " + shoppingCart);
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(shoppingCart);
            transaction.commit();
            log.info("Shopping cart was created: " + shoppingCart);
            return shoppingCart;
        } catch (Exception e) {
            log.error("Can't create shopping cart: " + shoppingCart + "\nerror: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cant add shopping cart: " + shoppingCart, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public ShoppingCart getByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<ShoppingCart> query = session.createQuery("FROM ShoppingCart "
                    + "WHERE id = :userId ", ShoppingCart.class);
            query.setParameter("userId", user.getId());
            ShoppingCart cart = query.getSingleResult();
            Hibernate.initialize(cart.getTickets());
            return cart;
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        Session session = null;
        log.info("Updating shopping cart: " + shoppingCart);
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(shoppingCart);
            transaction.commit();
            log.info("Shopping cart was updated: " + shoppingCart);
        } catch (Exception e) {
            log.error("Can't update shopping cart: " + shoppingCart + "\nerror: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cant update shopping cart: " + shoppingCart, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}

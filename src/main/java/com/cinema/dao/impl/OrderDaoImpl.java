package com.cinema.dao.impl;

import com.cinema.dao.OrderDao;
import com.cinema.exception.DataProcessingException;
import com.cinema.model.Order;
import com.cinema.model.User;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@Log4j
public class OrderDaoImpl implements OrderDao {
    private final SessionFactory sessionFactory;

    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order add(Order order) {
        Transaction transaction = null;
        Session session = null;
        log.info("Creating order: " + order);
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            log.info("Order was created: " + order);
            return order;
        } catch (Exception e) {
            log.error("Can't create order: " + order + "\nerror: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cant add order: " + order, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Order o "
                    + "join fetch o.tickets "
                    + "join fetch o.user "
                    + "where o.user = :user", Order.class)
                    .setParameter("user", user)
                    .getResultList();
        }
    }
}

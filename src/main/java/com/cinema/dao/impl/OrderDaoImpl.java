package com.cinema.dao.impl;

import com.cinema.Main;
import com.cinema.dao.OrderDao;
import com.cinema.exception.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.Order;
import com.cinema.model.User;
import com.cinema.util.HibernateUtil;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = Logger.getLogger(OrderDaoImpl.class);

    @Override
    public Order add(Order order) {
        Transaction transaction = null;
        Session session = null;
        logger.info("Creating order: " + order);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            logger.info("Order was created: " + order);
            return order;
        } catch (Exception e) {
            logger.error("Can't create order: " + order + "\nerror: ", e);
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Order o "
                    + "join fetch o.tickets "
                    + "join fetch o.user "
                    + "where o.user = :user", Order.class)
                    .setParameter("user", user)
                    .getResultList();
        }
    }
}

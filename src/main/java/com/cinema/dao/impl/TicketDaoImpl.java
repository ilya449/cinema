package com.cinema.dao.impl;

import com.cinema.dao.TicketDao;
import com.cinema.exception.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.Ticket;
import com.cinema.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class TicketDaoImpl implements TicketDao {
    private static final Logger logger = Logger.getLogger(TicketDaoImpl.class);

    @Override
    public Ticket add(Ticket ticket) {
        Transaction transaction = null;
        Session session = null;
        logger.info("Creating ticket: " + ticket);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();
            logger.info("Ticket was created: " + ticket);
            return ticket;
        } catch (Exception e) {
            logger.error("Can't create ticket: " + ticket + "\nerror:", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cant add ticket: " + ticket, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}

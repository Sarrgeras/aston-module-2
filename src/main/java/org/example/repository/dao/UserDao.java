package org.example.repository.dao;

import org.example.config.HibernateConfig;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao implements UserRepository<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Override
    public User create(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateConfig.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            logger.info("Starting user process for creating to db");
            transaction = session.beginTransaction();

            session.persist(user);
            transaction.commit();
            logger.info("Created user in db");
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Transaction rolled back");
            }
            logger.error("Error creating user to db");
            throw new HibernateException("Failed creating user", e);
        }
    }

    @Override
    public User read(Long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateConfig.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            logger.info("Starting user process for reading from db");
            transaction = session.beginTransaction();
            User user = session.byId(User.class).load(id);
            transaction.commit();
            logger.info("Read user from db");
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Transaction rolled back");
            }
            logger.error("Error reading user from db");
            throw new HibernateException("Failed reading user", e);
        }
    }

    @Override
    public User update(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateConfig.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            logger.info("Starting user process for updating to db");
            transaction = session.beginTransaction();

            session.merge(user);
            transaction.commit();
            logger.info("Updated user in db");
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Transaction rolled back");
            }
            logger.error("Error updating user to db");
            throw new HibernateException("Failed updating user", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateConfig.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            logger.info("Starting user process for deleting in db");
            transaction = session.beginTransaction();

            User user = session.byId(User.class).load(id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
            logger.info("Deleted user in db");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Transaction rolled back");
            }
            logger.error("Error deleting user in db");
            throw new HibernateException("Failed deleting user", e);
        }
    }

}

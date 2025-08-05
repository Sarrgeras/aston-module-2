package org.example.repository.dao;

import org.example.config.HibernateConfig;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao implements UserRepository<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    @Override
    public User create(User user) {
        try (SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            logger.info("Starting user process for creating to db");
            session.beginTransaction();

            session.persist(user);
            session.getTransaction().commit();
            logger.info("Created user in db");
            return user;
        }
        catch (Exception e){
            logger.error("Error creating user to db");
            throw new HibernateException("Failed creating user", e);
        }
    }

    @Override
    public User read(Long id) {
        try (SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            logger.info("Starting user process for reading from db");
            logger.info("Read user from db");
            return session.byId(User.class).load(id);
        }
        catch (Exception e){
            logger.error("Error reading user from db");
            throw new HibernateException("Failed reading user", e);
        }
    }

    @Override
    public User update(User user) {
        try (SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            logger.info("Starting user process for updating to db");
            session.beginTransaction();

            session.merge(user);
            session.getTransaction().commit();
            logger.info("Updated user in db");
            return user;
        }
        catch (Exception e){
            logger.error("Error updating user to db");
            throw new HibernateException("Failed updating user", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            logger.info("Starting user process for deleting in db");
            session.beginTransaction();

            User user = session.byId(User.class).load(id);
            if (user != null) {
                session.remove(user);
            }
            session.getTransaction().commit();
            logger.info("Deleted user in db");
        }
        catch (Exception e){
            logger.error("Error deleting user in db");
            throw new HibernateException("Failed deleting user", e);
        }
    }

}

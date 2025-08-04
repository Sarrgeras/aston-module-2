package org.example.config;

import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateConfig {
    private static final Logger logger = LoggerFactory.getLogger(HibernateConfig.class);
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        try{
            Configuration configuration = new Configuration()
                    .configure()
                    .addAnnotatedClass(User.class);

            sessionFactory = configuration.buildSessionFactory();
            logger.info("Hibernate SessionFactory initialized successfully");

        }
        catch (Throwable ex){
            logger.error("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            logger.info("Hibernate SessionFactory closed successfully");
        }
    }
}

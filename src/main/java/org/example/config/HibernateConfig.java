package org.example.config;

import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateConfig {
    private static final Logger logger = LoggerFactory.getLogger(HibernateConfig.class);
    private static final HibernateConfig INSTANCE = new HibernateConfig();
    private final SessionFactory sessionFactory;

    private HibernateConfig(){
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
    }

    public static SessionFactory getSessionFactory() {
        return INSTANCE.sessionFactory;
    }

    public static void shutdown() {
        if (INSTANCE.sessionFactory != null) {
            INSTANCE.sessionFactory.close();
            logger.info("Hibernate SessionFactory closed successfully");
        }
    }
}

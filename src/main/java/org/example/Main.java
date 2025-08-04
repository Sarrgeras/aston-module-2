package org.example;

import org.example.config.HibernateConfig;
import org.example.controller.UserController;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            UserController controller = new UserController();
            controller.start();
        } finally {
            HibernateConfig.shutdown();
        }
    }
}
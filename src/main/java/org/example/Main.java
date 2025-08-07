package org.example;

import org.example.config.HibernateConfig;
import org.example.controller.UserController;

public class Main {
    public static void main(String[] args) {
        try {
            UserController controller = new UserController();
            controller.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            HibernateConfig.shutdown();
        }
    }
}
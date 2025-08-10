package org.example;

import org.example.config.HibernateConfig;
import org.example.controller.UserController;
import org.example.repository.dao.UserDao;
import org.example.service.UserService;

public class Main {
    public static void main(String[] args) {
        try {
            UserDao userDao = new UserDao();
            UserService userService = new UserService(userDao);
            UserController controller = new UserController(userService);

            controller.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            HibernateConfig.shutdown();
        }
    }
}
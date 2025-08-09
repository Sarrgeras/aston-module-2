package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService = new UserService();
    private final Scanner scanner = new Scanner(System.in);

    public void start(){
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createUser();
                case 2 -> getUserById();
                case 3 -> updateUser();
                case 4 -> deleteUser();
                case 0 -> {
                    return;
                }
                default -> System.out.println("неправильный выбор");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n1. Создать позльзователя");
        System.out.println("2. Выбрать пользователя по id");
        System.out.println("3. обновить пользователя");
        System.out.println("4. Удалить пользователя");
        System.out.println("0. выход");
    }

    private void createUser(){
        try {
            logger.info("Starting user creation process");
            System.out.print("Введите имя: ");
            String name = scanner.nextLine();

            System.out.print("Введите почту: ");
            String email = scanner.nextLine();

            User user = userService.createUser(name, email);
            logger.info("Created new user: {}", user);
        }
        catch (Exception e){
            logger.error("Error creating user", e);
            System.out.println("Failed creating user " + e.getMessage());

        }
    }

    private void getUserById(){
        try{
            logger.info("Starting user getting process");
            System.out.print("введите id: ");
            Long id = scanner.nextLong();
            User user = userService.getUserById(id);
            System.out.println(user != null ? user : "пользователь не найден");
            logger.info("get user: {}", user);
        }
        catch (Exception e){
            logger.error("Error getting user", e);
            System.out.println("Failed getting user " + e.getMessage());
        }
    }

    private void updateUser(){
        try{
            logger.info("Starting user updating process");
            System.out.print("введите id: ");
            Long id = scanner.nextLong();
            scanner.nextLine();

            User user = userService.getUserById(id);
            if (user == null) {
                System.out.println("пользователь не найден");
                return;
            }

            System.out.print("Введите имя (текущее: " + user.getName() + "): ");
            user.setName(scanner.nextLine());

            System.out.print("Введите почту (текущее: " + user.getEmail() + "): ");
            user.setEmail(scanner.nextLine());

            userService.updateUser(user);
            logger.info("Updated user: {}", user);
        }
        catch (Exception e){
            logger.error("Error updating user", e);
            System.out.println("Failed updating user " + e.getMessage());
        }
    }

    private void deleteUser(){
        try{
            logger.info("Starting user deleting process");
            System.out.print("введите id: ");
            Long id = scanner.nextLong();
            userService.deleteUser(id);
            logger.info("Deleted user with id: {}", id);
        }catch (Exception e){
            logger.error("Error deleting user", e);
            System.out.println("Failed deleting user " + e.getMessage());
        }
    }
}

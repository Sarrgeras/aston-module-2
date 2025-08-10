package org.example.repository.dao;

import org.example.config.HibernateConfig;
import org.example.model.User;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@Testcontainers
class UserDaoTest {

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("user_service")
                    .withUsername("root")
                    .withPassword("03042002");

    private static UserDao userDao;
    private static User user;

    @BeforeAll
    static void setup() {
        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());
        System.setProperty("hibernate.hbm2ddl.auto", "update");
        System.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        user = User.builder()
                .name("Vlad")
                .email("mlvlad@mail.ru")
                .created_at(LocalDateTime.now())
                .build();

        userDao = new UserDao();
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.shutdown();
    }

    @BeforeEach
    void clearDatabase() {
        try (var session = HibernateConfig.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            session.createMutationQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        }
    }

    @Test
    @DisplayName("Создание пользователя в БД")
    void createUser() {
        User savedUser = userDao.create(user);

        assertNotNull(savedUser.getId(), "ID должен быть присвоен");
        assertEquals("Vlad", savedUser.getName());
        assertEquals("mlvlad@mail.ru", savedUser.getEmail());
    }

    @Test
    @DisplayName("Чтение пользователя из БД")
    void getUserById() {
        User savedUser = userDao.create(user);

        User foundUser = userDao.read(savedUser.getId());

        assertNotNull(foundUser.getId(), "ID должен быть присвоен");
        assertEquals(savedUser.getId(), foundUser.getId());
        assertEquals("Vlad", foundUser.getName());
    }

    @Test
    @DisplayName("Обнорвление пользователя в БД")
    void updateUser(){
        User savedUser = userDao.create(user);

        savedUser.setName("NewName");
        savedUser.setEmail("NewMail@mail.ru");

        User updatedUser = userDao.update(savedUser);

        assertNotNull(updatedUser, "Пользователь пустой");
        assertEquals("NewName", updatedUser.getName(), "Несовпадение в именах");
        assertEquals("NewMail@mail.ru", updatedUser.getEmail(), "Несовпадение в именах почты");

        User fromDb = userDao.read(updatedUser.getId());
        assertEquals("NewName", fromDb.getName(), "Имя должно быть обновлено в БД");
    }

    @Test
    @DisplayName("Удаление пользователя в БД")
    void deleteUser(){
        User savedUser = userDao.create(user);
        Long id = savedUser.getId();

        userDao.delete(id);

        User deletedUser = userDao.read(id);
        assertNull(deletedUser, "Пользователь должен быть удален из БД");
    }
}
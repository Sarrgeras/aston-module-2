package org.example.service;

import org.example.config.HibernateConfig;
import org.example.model.User;
import org.example.repository.dao.UserDao;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private User user;
    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @BeforeEach
    void setUp(){
        userService = new UserService(userDao);
        user = User.builder()
                .id(1L)
                .name("Vlad")
                .email("mlvlad@mail.ru")
                .created_at(LocalDateTime.now())
                .build();
    }

    @Test
    void createUser(){
        Mockito.when(userDao.create(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser("Vlad", "mlvlad@mail.ru");

        assertEquals("Vlad", createdUser.getName(), "Несовпадение в именах");
        Mockito.verify(userDao).create(any(User.class));
    }

    @Test
    void createUser_Exception() {
        when(userDao.create(any(User.class))).thenThrow(new HibernateException("Error when create User"));

        HibernateException ex = Assertions.assertThrows(HibernateException.class,
                () -> userService.createUser("Vlad", "mlvlad@mail.ru"));

        assertEquals("Error when create User", ex.getMessage());
        verify(userDao).create(any(User.class));
    }

    @Test
    void getUserById(){
        Long id = 1L;

        Mockito.when(userDao.read(id)).thenReturn(user);

        User readUser = userService.getUserById(id);

        assertNotNull(readUser, "Пользователь пустой");
        assertEquals(id, readUser.getId(), "Несовпадение в id");
        assertEquals("Vlad", readUser.getName(), "Несовпадение в именах");
        assertEquals(user.getEmail(), readUser.getEmail(), "Несовпадение в именах почты");

        Mockito.verify(userDao).read(id);
    }

    @Test
    void getUserById_Exception(){
        Long id = 1L;

        Mockito.when(userDao.read(id)).thenThrow(new HibernateException("Error when get User by id"));

        HibernateException ex = Assertions.assertThrows(HibernateException.class,
                () -> userService.getUserById(id));

        assertEquals("Error when get User by id", ex.getMessage());

        Mockito.verify(userDao).read(id);
    }

    @Test
    void updateUser(){

        User newUser = User.builder()
                .name("NewName")
                .email("NewMail@mail.ru")
                .build();

        Mockito.when(userDao.update(any(User.class))).thenReturn(newUser);

        User updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser, "Пользователь пустой");
        assertEquals("NewName", updatedUser.getName(), "Несовпадение в именах");
        assertEquals("NewMail@mail.ru", updatedUser.getEmail(), "Несовпадение в именах почты");
        Mockito.verify(userDao).update(any(User.class));
    }

    @Test
    void updateUser_Exception(){

        User newUser = User.builder()
                .name("NewName")
                .email("NewMail@mail.ru")
                .build();

        Mockito.when(userDao.update(any(User.class))).thenThrow(new HibernateException("Error when update User"));

        HibernateException ex = Assertions.assertThrows(HibernateException.class,
                () -> userService.updateUser(newUser));

        assertEquals("Error when update User", ex.getMessage());

        Mockito.verify(userDao).update(any(User.class));
    }


    @Test
    void deleteUser(){
        Long id = 1L;

        doNothing().when(userDao).delete(id);

        userService.deleteUser(id);

        verify(userDao, times(1)).delete(id);
    }

    @Test
    void deleteUser_Exception(){
        Long id = 1L;

        doThrow(new HibernateException("Error when delete user")).when(userDao).delete(id);

        HibernateException ex = Assertions.assertThrows(HibernateException.class,
                () -> userService.deleteUser(id));

        assertEquals("Error when delete user", ex.getMessage());

        verify(userDao, times(1)).delete(id);
    }
}

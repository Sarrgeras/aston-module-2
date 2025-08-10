package org.example.service;

import org.example.model.User;
import org.example.repository.dao.UserDao;
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
    void deleteUser(){
        Long id = 1L;

        doNothing().when(userDao).delete(id);

        userService.deleteUser(id);

        verify(userDao, times(1)).delete(id);
    }
}

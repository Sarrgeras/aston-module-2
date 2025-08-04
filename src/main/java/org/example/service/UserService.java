package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.repository.dao.UserDao;

import java.time.LocalDateTime;

public class UserService {
    private final UserDao userDao = new UserDao();

    public User createUser(String name, String email){
        User user = User.builder()
                .name(name)
                .email(email)
                .created_at(LocalDateTime.now())
                .build();
        return userDao.create(user);
    }

    public User getUserById(Long id){
        return userDao.read(id);
    }

    public User updateUser(User user) {
        return userDao.update(user);
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}

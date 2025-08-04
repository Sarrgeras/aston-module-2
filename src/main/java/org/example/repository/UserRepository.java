package org.example.repository;

import org.example.model.User;

import java.util.List;

public interface UserRepository<T> {
    T create(T user);
    T read(Long id);
    T update(T user);
    void delete(Long id);


}

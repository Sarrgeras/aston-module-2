package org.example.repository;

import org.example.model.User;
import org.hibernate.HibernateException;

import java.util.List;

public interface UserRepository<T> {
    T create(T user) throws HibernateException;
    T read(Long id) throws HibernateException;
    T update(T user) throws HibernateException;
    void delete(Long id) throws HibernateException;


}

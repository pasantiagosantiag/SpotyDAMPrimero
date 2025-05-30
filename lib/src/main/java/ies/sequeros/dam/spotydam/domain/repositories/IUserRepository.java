package ies.sequeros.dam.spotydam.domain.repositories;

import ies.sequeros.dam.spotydam.domain.model.User;

import java.util.List;

public interface IUserRepository {
    public void add(User user) throws NoSuchFieldException;

    public User findByEmailAndPassword(String email, String password);
    public void delete(User user) throws NoSuchFieldException;
    public void update(User user) throws NoSuchFieldException;
    public List<User> findAll();
    public void close();
}


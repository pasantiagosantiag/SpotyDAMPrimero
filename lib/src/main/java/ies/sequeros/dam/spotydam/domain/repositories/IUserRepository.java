package ies.sequeros.dam.spotydam.domain.repositories;

import ies.sequeros.dam.spotydam.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserRepository {
    public void add(User user);

    public User findByNickAndPassword(String nick, String password);
    public void delete(User user);
    public void update(User user);
    public List<User> findAll();
    public void close();
    public void removeAll();
    public User findById(UUID id);
    public User findByNick(String nick);
}


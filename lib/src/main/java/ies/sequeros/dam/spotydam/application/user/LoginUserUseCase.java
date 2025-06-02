package ies.sequeros.dam.spotydam.application.user;

import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IUserRepository;

public class LoginUserUseCase {
    private IUserRepository repository;
    public LoginUserUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    public User login(String nick, String password) {
        User user = repository.findByNick(nick);
        if(user != null && user.getPassword().equals(password)) {
            return user;
        }else
            return null;
    }
}

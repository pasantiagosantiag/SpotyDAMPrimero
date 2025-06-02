package ies.sequeros.dam.spotydam.application.user;

import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IUserRepository;

import java.util.List;

public class ListAllUserUseCase {
    private IUserRepository userRepository;
    public ListAllUserUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> execute(){
        return userRepository.findAll();
    }
}

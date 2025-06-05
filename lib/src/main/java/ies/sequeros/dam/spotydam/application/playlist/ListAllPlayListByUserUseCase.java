package ies.sequeros.dam.spotydam.application.playlist;

import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;

import java.util.List;

public class ListAllPlayListByUserUseCase {
    private IPlayListRepository repository;
    public ListAllPlayListByUserUseCase(IPlayListRepository repository) {
        this.repository = repository;
    }
    public List<PlayList> execute(User user){


        if(user!=null) {
            if(user.getRole().equals(User.Role.ADMIN))
                return repository.findAll();
            else
                return repository.findByUser(user);
        }
        else
            throw new IllegalArgumentException("User is null");
    }
}

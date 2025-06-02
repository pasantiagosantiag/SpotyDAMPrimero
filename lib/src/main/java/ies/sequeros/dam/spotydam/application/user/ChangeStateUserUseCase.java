package ies.sequeros.dam.spotydam.application.user;

import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IUserRepository;

public class ChangeStateUserUseCase {
    private IUserRepository repository;
    public ChangeStateUserUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    public void execute(User user, User.Status newState) {
        user.setStatus(newState);
        repository.update(user);
    }
}

package ies.sequeros.dam.spotydam.application.user;

import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IUserRepository;

public class SetRoleUserUseCase {
    private IUserRepository repository;
    public SetRoleUserUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    public void execute(User user, User.Role newRole) {
        user.setRole(newRole);
        repository.update(user);
    }
}

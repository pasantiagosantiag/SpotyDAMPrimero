package ies.sequeros.dam.spotydam.application.user;

import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IFilesRepository;
import ies.sequeros.dam.spotydam.domain.repositories.IUserRepository;

public class AddUserUseCase {
    private IUserRepository repository;
    private IFilesRepository filesRepository;
    public AddUserUseCase(IUserRepository repository, IFilesRepository filesRepository) {
        this.repository = repository;
        this.filesRepository = filesRepository;
    }
    public void execute(User user) {
        //se almacena la imagen
        String nuevoPath=filesRepository.save("User"+user.getId().toString(),user.getImage());
        user.setImage(nuevoPath);
        repository.add(user);
    }
}

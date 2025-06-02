package ies.sequeros.dam.spotydam.application.user;

import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IFilesRepository;
import ies.sequeros.dam.spotydam.domain.repositories.IUserRepository;

public class UpdateUserUseCase {
    private IUserRepository repository;
    private IFilesRepository filesRepository;
    public UpdateUserUseCase(IUserRepository repository, IFilesRepository filesRepository) {
        this.repository = repository;
        this.filesRepository = filesRepository;
    }
    public void execute(User user) {
        User original=repository.findById(user.getId());
        if(original==null) {
            throw  new IllegalArgumentException("User with id "+user.getId()+" not found");
        }
        //se ha modifica la imagen, se borra la anterior
        if(!original.getImage().equals(user.getImage())) {
            String nuevoPath=this.filesRepository.replace("User"+user.getId().toString(),user.getImage(),original.getImage());
            user.setImage(nuevoPath);
        }
        repository.update(user);
    }
}

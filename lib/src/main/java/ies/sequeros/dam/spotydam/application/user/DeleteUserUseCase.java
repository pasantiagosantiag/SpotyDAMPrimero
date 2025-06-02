package ies.sequeros.dam.spotydam.application.user;

import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IFilesRepository;
import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;
import ies.sequeros.dam.spotydam.domain.repositories.IUserRepository;

public class DeleteUserUseCase {
    private IUserRepository repository;
    private IPlayListRepository playListRepository;
    private ISongRepository songRepository;
    private IFilesRepository filesRepository;
    public DeleteUserUseCase(IUserRepository repository,
                             IFilesRepository filesRepository,
                             ISongRepository songRepository,
                             IPlayListRepository playListRepository) {
        this.repository = repository;
        this.songRepository = songRepository;
        this.playListRepository = playListRepository;
        this.filesRepository = filesRepository;
    }
    public void execute(User user) {
        filesRepository.delete(user.getImage());
        repository.delete(user);
    }
}

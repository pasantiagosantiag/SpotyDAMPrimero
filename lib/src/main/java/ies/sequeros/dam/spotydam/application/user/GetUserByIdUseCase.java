package ies.sequeros.dam.spotydam.application.user;

import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;
import ies.sequeros.dam.spotydam.domain.repositories.IUserRepository;

import java.util.Optional;
import java.util.UUID;

public class GetUserByIdUseCase {
    private IUserRepository repository;
    private IPlayListRepository playListRepository;
    private ISongRepository songRepository;
    public GetUserByIdUseCase(IUserRepository repository,
                             ISongRepository songRepository,
                             IPlayListRepository playListRepository) {
        this.repository = repository;
        this.songRepository = songRepository;
        this.playListRepository = playListRepository;
    }
    public Optional<User> execute(UUID id) {
        User u=repository.findById(id);
        if(u==null)
            return Optional.empty();
        else
            return Optional.of(u);

    }
}

package ies.sequeros.dam.spotydam.application.song;

import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

import java.util.List;

/**
 * Obtiene todas las canciones en caso de ser administrador
 * o las canciones públicas o que son de eese propietario
 */
public class ListAllSongsByUserAndPublicUseCase {
    private ISongRepository repository;
    public ListAllSongsByUserAndPublicUseCase(ISongRepository repository) {
        this.repository = repository;
    }
    public List<Song> execute(User user){
        if(user!=null) {
            if(user.getRole().equals(User.Role.ADMIN))
            return repository.findAll();
            else
                return repository.findByOwenerIdOrIsPublic(user.getId());
        }
        else
            throw new IllegalArgumentException("User is null");
    }
}

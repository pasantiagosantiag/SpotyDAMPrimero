package ies.sequeros.dam.spotydam.application.playlist;

import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

import java.util.List;

public class ListAllPlayListUseCase {
    private ISongRepository repository;
    public ListAllPlayListUseCase(ISongRepository repository) {
        this.repository = repository;
    }
    public List<Song> execute(){
        return repository.findAll();
    }
}

package ies.sequeros.dam.spotydam.application.song;

import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

import java.util.List;

public class ListAllSongsUseCase {
    private ISongRepository repository;
    public ListAllSongsUseCase(ISongRepository repository) {
        this.repository = repository;
    }
    public List<Song> execute(){
        return repository.findAll();
    }
}

package ies.sequeros.dam.spotydam.application.playlist;

import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

import java.util.List;

public class ListAllPlayListUseCase {
    private IPlayListRepository repository;
    public ListAllPlayListUseCase(IPlayListRepository repository) {
        this.repository = repository;
    }
    public List<PlayList> execute(){
        return repository.findAll();
    }
}

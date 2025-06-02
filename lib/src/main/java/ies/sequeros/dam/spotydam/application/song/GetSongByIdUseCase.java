package ies.sequeros.dam.spotydam.application.song;

import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

import java.util.Optional;
import java.util.UUID;

public class GetSongByIdUseCase {
    private IPlayListRepository playListRepository;
    private ISongRepository repository;
    public GetSongByIdUseCase(ISongRepository repository,

                              IPlayListRepository playListRepository) {
        this.repository = repository;

        this.playListRepository = playListRepository;
    }
    public Optional<Song> execute(UUID id) {
        Song item=repository.findById(id);
        if(item==null)
            return Optional.empty();
        else
            return Optional.of(item);

    }
}

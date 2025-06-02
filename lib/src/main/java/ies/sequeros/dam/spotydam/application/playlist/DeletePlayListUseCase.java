package ies.sequeros.dam.spotydam.application.playlist;

import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.IFilesRepository;

import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

public class DeletePlayListUseCase {

    private IPlayListRepository playListRepository;
    private ISongRepository repository;
    private IFilesRepository imagesRepository;
    private IFilesRepository songsRepository;
    public DeletePlayListUseCase(ISongRepository repository,
                                 IPlayListRepository playListRepository,
                                 IFilesRepository imagesRepository,
                                 IFilesRepository songRepository) {
        this.repository = repository;

        this.playListRepository = playListRepository;
        //gestión de ficheros
        this.songsRepository = songRepository;
        this.imagesRepository = imagesRepository;
    }
    public void execute(Song item) {
        imagesRepository.delete(item.getPathImage());
        songsRepository.delete(item.getPath());
        repository.delete(item);
    }
}

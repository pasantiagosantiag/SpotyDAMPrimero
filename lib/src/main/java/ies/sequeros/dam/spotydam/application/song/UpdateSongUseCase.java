package ies.sequeros.dam.spotydam.application.song;

import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.IFilesRepository;
import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

public class UpdateSongUseCase {
    private ISongRepository repository;
    private IFilesRepository imagesRepository;
    private IFilesRepository songsRepository;
    private IPlayListRepository playListRepository;
    public UpdateSongUseCase(ISongRepository repository,
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
        Song original=repository.findById(item.getId());
        if(original==null) {
            throw  new IllegalArgumentException("Song with id "+item.getId()+" not found");
        }
        //se ha modifica la canción, se borra la anterior
        if(!original.getPath().equals(item.getPath())) {
            String nuevoPath=this.songsRepository.replace("Song"+item.getId().toString(),item.getPath(),original.getPath());
            item.setPath(nuevoPath);
        }
        //se ha modifica la imagen, se borra la anterior
        if(!original.getPathImage().equals(item.getPathImage())) {
            String nuevoPath=this.imagesRepository.replace("SongImg"+item.getId().toString(),item.getPathImage(),original.getPathImage());
            item.setPathImage(nuevoPath);
        }
        repository.update(item);
    }
}

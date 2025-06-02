package ies.sequeros.dam.spotydam.application.song;

import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.IFilesRepository;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

public class AddSongUseCase {
    private ISongRepository repository;

    private IFilesRepository imagesRepository;
    private IFilesRepository songsRepository;
    public AddSongUseCase(ISongRepository repository, IFilesRepository imagesRepository, IFilesRepository songsRepository) {
        this.repository = repository;
        this.imagesRepository = imagesRepository;
        this.songsRepository= songsRepository;
    }
    public void execute(Song item) {
        //se almacena la cancion y la imagen
        String nuevoPath=songsRepository.save(item.getId().toString(),item.getPath());
        item.setPath(nuevoPath);
        String nuevoPathImagen=imagesRepository.save(item.getId().toString(),item.getPathImage());
        item.setPathImage(nuevoPathImagen);
        repository.add(item);
    }
}

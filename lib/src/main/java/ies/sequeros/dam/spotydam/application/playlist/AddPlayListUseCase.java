package ies.sequeros.dam.spotydam.application.playlist;

import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.IFilesRepository;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

public class AddPlayListUseCase {
    private ISongRepository repository;

    private IFilesRepository imagesRepository;
    private IFilesRepository songsRepository;
    public AddPlayListUseCase(ISongRepository repository, IFilesRepository imagesRepository, IFilesRepository songsRepository) {
        this.repository = repository;
        this.imagesRepository = imagesRepository;
        this.songsRepository= songsRepository;
    }
    public void execute(Song item) {
        //se almacena la cancion y la imagen
        String nuevoPath=songsRepository.save(item.getId().toString(),item.getPath());
        item.setPath(nuevoPath);
        String nuevoPathImagen=imagesRepository.save(item.getId().toString(),item.getPath());
        item.setPathImage(nuevoPathImagen);
        repository.add(item);
    }
}

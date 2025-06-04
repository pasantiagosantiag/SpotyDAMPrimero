package ies.sequeros.dam.spotydam.application.playlist;

import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.IFilesRepository;
import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;
import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;

public class AddPlayListUseCase {
    private IPlayListRepository repository;

    private IFilesRepository imagesRepository;

    public AddPlayListUseCase(IPlayListRepository repository, IFilesRepository imagesRepository) {
        this.repository = repository;
        this.imagesRepository = imagesRepository;

    }
    public void execute(PlayList item) {
        //se almacena la cancion y la imagen

        String nuevoPathImagen=imagesRepository.save(item.getId().toString(),item.getImage());
        item.setImage(nuevoPathImagen);
        repository.add(item);
    }
}

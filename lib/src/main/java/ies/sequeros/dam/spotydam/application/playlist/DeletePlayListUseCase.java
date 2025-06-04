package ies.sequeros.dam.spotydam.application.playlist;

import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.IFilesRepository;

import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

public class DeletePlayListUseCase {

    private IPlayListRepository playListRepository;

    private IFilesRepository imagesRepository;

    public DeletePlayListUseCase(
                                 IPlayListRepository playListRepository,
                                 IFilesRepository imagesRepository
                                ) {


        this.playListRepository = playListRepository;
        //gestión de ficheros

        this.imagesRepository = imagesRepository;
    }
    public void execute(PlayList item) {
        imagesRepository.delete(item.getImage());

        this.playListRepository.delete(item);
    }
}

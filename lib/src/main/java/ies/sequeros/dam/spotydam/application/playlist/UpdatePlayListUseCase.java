package ies.sequeros.dam.spotydam.application.playlist;

import ies.sequeros.dam.spotydam.application.playlist.model.PlayListWithSongs;
import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.IFilesRepository;
import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

public class UpdatePlayListUseCase {

    private IFilesRepository imagesRepository;

    private IPlayListRepository playListRepository;
    public UpdatePlayListUseCase(
                                 IPlayListRepository playListRepository,
                                 IFilesRepository imagesRepository
                                ) {


        this.playListRepository = playListRepository;
        //gestión de ficheros
        this.imagesRepository = imagesRepository;
    }
    public void execute(PlayListWithSongs item) {
        PlayList original=playListRepository.findById(item.getId());
        if(original==null) {
            throw  new IllegalArgumentException("Playlist with id "+item.getId()+" not found");
        }

        //se ha modifica la imagen, se borra la anterior
        if(!original.getImage().equals(item.getImage())) {
            String nuevoPath=this.imagesRepository.replace("SongImg"+item.getId().toString(),item.getImage(),original.getImage());
            item.setImage(nuevoPath);
        }
        playListRepository.update(item.toPlayList());
    }
}

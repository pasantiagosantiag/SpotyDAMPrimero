package ies.sequeros.dam.spotydam.application.playlist;

import ies.sequeros.dam.spotydam.application.playlist.model.PlayListWithSongs;
import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

import java.util.List;
import java.util.UUID;

/**
 * obtener un lista de reproducción con las
 * canciones asociadas
 */
public class GetPlayListWithtSongsUseCase {
    private IPlayListRepository repository;
    private ISongRepository songRepository;

    public GetPlayListWithtSongsUseCase(IPlayListRepository repository, ISongRepository songRepository) {
        this.repository = repository;
        this.songRepository = songRepository;
    }

    public PlayListWithSongs execute(UUID playlistId) {
        PlayList pl = repository.findById(playlistId);
        PlayListWithSongs plws = null;
        if (pl != null) {
            plws = new PlayListWithSongs(pl);

        }

        var songs = this.songRepository.findByListOfIds(pl.getSongIds());
        plws.setSongs(songs);
        return plws;
    }
}

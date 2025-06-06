package ies.sequeros.dam.spotydam.application.playlist;

import ies.sequeros.dam.spotydam.application.playlist.model.PlayListWithSongs;
import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;

public class AddSongToPlayListUseCase {
    private IPlayListRepository repository;

    public AddSongToPlayListUseCase(IPlayListRepository repository) {
        this.repository = repository;
    }
    public void execute(PlayList playList, Song song) {
        if(playList == null || song == null) {
            throw new NullPointerException("PlayList or song is null");
        }
        playList.addSong(song.getId());
        repository.update(playList);
    }
}

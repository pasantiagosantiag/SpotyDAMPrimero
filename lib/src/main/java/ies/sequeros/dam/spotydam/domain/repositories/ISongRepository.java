package ies.sequeros.dam.spotydam.domain.repositories;

import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface ISongRepository {
    public void add(Song Song) ;

    public void delete(Song Song) ;
    public void update(Song Song);
    public List<Song> findAll();
    public Song findById(UUID id);
    public List<Song> findByPublic();
    public List<Song> findByOwnerId(UUID userId);
    public List<Song> findByPublicByGenre(Song.Genre genere);
    public void close();
    public void removeAll();
}

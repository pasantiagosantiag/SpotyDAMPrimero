package ies.sequeros.dam.spotydam.domain.repositories;

import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.infraestructure.files.SongRepositoryInFile;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ISongRepositoryTest  {
    private ISongRepository repository;
    private Song sampleSong;

    @Before
    public void setUp() {
        repository = new SongRepositoryInFile("songs.json");
        repository.removeAll();
        /*sampleSong = new Song(
                "Test Song",
                List.of(Song.Genre.ROCK),
                "/songs/test.mp3",
                UUID.randomUUID(),
                "Test Author",
                "A test song",
                LocalDate.now(),
                true
        );*/
    }

    @Test
    public void testAddAndFindById() throws NoSuchFieldException {
        repository.add(sampleSong);
        Song found = repository.findById(sampleSong.getId());
        assertEquals(sampleSong.getName(), found.getName());
    }

    @Test
    public void testDelete() throws NoSuchFieldException {
        repository.add(sampleSong);
        repository.delete(sampleSong);
        repository.findById(sampleSong.getId()); // debe lanzar excepción
    }

    @Test
    public void testUpdate() throws NoSuchFieldException {
        repository.add(sampleSong);
        sampleSong.setName("Updated Name");
        repository.update(sampleSong);
        assertEquals("Updated Name", repository.findById(sampleSong.getId()).getName());
    }

    @Test
    public void testFindByPublic() throws NoSuchFieldException {
        repository.add(sampleSong);
        List<Song> publicSongs = repository.findByPublic();
        assertEquals(1, publicSongs.size());
    }

    @Test
    public void testFindByOwnerId() throws NoSuchFieldException {
        repository.add(sampleSong);
        List<Song> owned = repository.findByOwnerId(sampleSong.getOwnerId());
        assertEquals(1, owned.size());
    }

    @Test
    public void testFindByPublicByGenre() throws NoSuchFieldException {
        repository.add(sampleSong);
        List<Song> rockSongs = repository.findByPublicByGenre(Song.Genre.ROCK);
        assertEquals(1, rockSongs.size());
    }

    @Test
    public void testFindAll() throws NoSuchFieldException {
        repository.add(sampleSong);
        assertEquals(1, repository.findAll().size());
    }



    @Test
    public void testUpdateNotFound()  {

        repository.delete(sampleSong);
        assertThrows(IllegalArgumentException.class, ()->{ repository.update(sampleSong);});

    }


}
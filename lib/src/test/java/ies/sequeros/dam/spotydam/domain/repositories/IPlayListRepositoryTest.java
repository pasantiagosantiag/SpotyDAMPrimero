package ies.sequeros.dam.spotydam.domain.repositories;

import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.infraestructure.files.PlayListRepositoryInFile;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertThrows;
@RunWith(JUnit4.class)
public class IPlayListRepositoryTest extends TestCase {
    private IPlayListRepository repository;
    private PlayList playlist;
    private UUID userId;

   /* @Before
    public void setup() {
        repository = new PlayListRepositoryInFile("playlists.json");
        repository.removeAll();
        userId = UUID.randomUUID();
        playlist = new PlayList("Favoritas", userId, "Mis canciones favoritas", LocalDate.now());
    }*/

    @Test
    public void testAddAndFindById() throws Exception {
        repository.add(playlist);
        PlayList found = repository.findById(playlist.getId());
        assertNotNull(found);
    }

    @Test
    public void testDelete() throws Exception {
        repository.add(playlist);
        repository.delete(playlist);
        assertNull(repository.findById(playlist.getId()));
    }

    @Test
    public void testUpdate() throws Exception {
        repository.add(playlist);
        playlist.setName("Actualizado");
        repository.update(playlist);
        PlayList updated = repository.findById(playlist.getId());
        assertNotNull(updated);
    }

    @Test
    public void testFindAll() throws Exception {
        repository.add(playlist);
        List<PlayList> all = repository.findAll();
        assertEquals(1, all.size());
    }

    @Test
    public void testFindByUserId() throws Exception {
        repository.add(playlist);
        List<PlayList> userPlaylists = repository.findByUserId(userId);
        assertEquals(1, userPlaylists.size());
    }



    // --- Excepciones esperadas ---

   @Test
    public void testAddNullThrows() {

        assertThrows(IllegalArgumentException.class, () -> repository.add(null));
    }

    @Test
    public void testDeleteNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> repository.update(null));
    }

    @Test
    public void testUpdateNonexistentThrows() {
        assertThrows(IllegalArgumentException.class, () -> repository.update(null));
    }
}
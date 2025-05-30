package ies.sequeros.dam.spotydam.domain.model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.*;

public class PlayListTest {

    private UUID ownerId;
    private LocalDate creationDate;

    @Before
    public void setUp() {
        ownerId = UUID.randomUUID();
        creationDate = LocalDate.now();
    }

    // --- Constructor válido ---
    @Test
    public void testValidPlaylistCreation() {
        PlayList playlist = new PlayList("Mi Playlist", ownerId, "Descripción", creationDate);
        assertNotNull(playlist.getId());
    }

    // --- Constructor inválido ---
    @Test
    public void testConstructorWithNullOwnerIdThrows() {
        NullPointerException ex = assertThrows(NullPointerException.class, () ->
                new PlayList("Playlist", null, "desc", creationDate)
        );
        assertEquals(ex.getClass(),NullPointerException.class);
    }

    @Test
    public void testConstructorWithNullCreationDateThrows() {
        assertThrows(NullPointerException.class, () ->
                new PlayList("Playlist", ownerId, "desc", null)
        );
    }

    @Test
    public void testConstructorWithBlankNameThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new PlayList("   ", ownerId, "desc", creationDate)
        );
    }

    // --- setName ---
    @Test
    public void testSetNameValid() {
        PlayList playlist = validPlaylist();
        playlist.setName("Nueva Lista");
        // No getter para verificar, pero sin excepción = OK
    }

    @Test
    public void testSetNameToNullThrows() {
        PlayList playlist = validPlaylist();
        assertThrows(IllegalArgumentException.class, () -> playlist.setName(null));
    }

    @Test
    public void testSetNameToBlankThrows() {
        PlayList playlist = validPlaylist();
        assertThrows(IllegalArgumentException.class, () -> playlist.setName("   "));
    }

    // --- addSong ---
    @Test
    public void testAddSongValid() {
        PlayList playlist = validPlaylist();
        playlist.addSong(UUID.randomUUID());
        // No getter, pero no debe lanzar excepción
    }

    @Test
    public void testAddSongWithNullThrows() {
        PlayList playlist = validPlaylist();
        assertThrows(NullPointerException.class, () -> playlist.addSong(null));
    }

    // --- like/dislike ---
    @Test
    public void testLikeWithValidUserId() {
        PlayList playlist = validPlaylist();
        playlist.like(UUID.randomUUID());
        // No getter, pero no debe lanzar excepción
    }

    @Test
    public void testDislikeWithValidUserId() {
        PlayList playlist = validPlaylist();
        playlist.dislike(UUID.randomUUID());
        // No excepción = OK
    }

    // --- Helper ---
    private PlayList validPlaylist() {
        return new PlayList("Chill", ownerId, "Relaxing songs", creationDate);
    }
}
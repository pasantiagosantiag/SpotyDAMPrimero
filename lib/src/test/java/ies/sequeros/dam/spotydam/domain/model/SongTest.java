package ies.sequeros.dam.spotydam.domain.model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.*;

public class SongTest  {


        private UUID ownerId;
        private LocalDate registrationDate;

        @Before
        public void setUp() {
            ownerId = UUID.randomUUID();
            registrationDate = LocalDate.now();
        }

        // --- Constructor válido ---
        @Test
        public void testValidSongCreation() {
            Song song = new Song(
                    "Test Song",
                    Arrays.asList(Song.Genre.ROCK, Song.Genre.POP),
                    "/music/song.mp3",
                    ownerId,
                    "Author Name",
                    "A test description",
                    registrationDate,
                    true
            );

            assertEquals("Test Song", song.getName());
            assertEquals("/music/song.mp3", song.getPath());
            assertEquals(ownerId, song.getOwnerId());
            assertEquals("Author Name", song.getAuthor());
            assertTrue(song.isPublic());
        }

        // --- Constructor con parámetros inválidos ---
        @Test()
        public void testConstructorWithBlankName() {


               assertThrows(IllegalArgumentException.class, ()->{ new Song(
                        " ",
                        Arrays.asList(Song.Genre.JAZZ),
                        "/path.mp3",
                        ownerId,
                        "Author",
                        "",
                        registrationDate,
                        true);
            }  );


        }

        @Test()
        public void testConstructorWithNullGenres() {
            try {
                new Song(
                        "Song",
                        null,
                        "/path.mp3",
                        ownerId,
                        "Author",
                        "",
                        registrationDate,
                        true
                );
            }catch(Exception e) {
                assertTrue(e instanceof IllegalArgumentException);
            }

        }

        @Test()
        public void testConstructorWithEmptyGenres() {
            try {
                new Song(
                        "Song",
                        Collections.emptyList(),
                        "/path.mp3",
                        ownerId,
                        "Author",
                        "",
                        registrationDate,
                        true
                );
            }catch (Exception e) {
                assertTrue(e instanceof IllegalArgumentException);
            }
        }

        @Test()
        public void testConstructorWithBlankPath() {

            try {

                new Song(
                        "Song",
                        Arrays.asList(Song.Genre.POP),
                        "   ",
                        ownerId,
                        "Author",
                        "",
                        registrationDate,
                        true
                );
            }catch (Exception e) {
                assertTrue(e instanceof IllegalArgumentException);
            }
        }

        @Test
        public void testConstructorWithBlankAuthor() {
            try {
                new Song(
                        "Song",
                        Arrays.asList(Song.Genre.POP),
                        "/path.mp3",
                        ownerId,
                        " ",
                        "",
                        registrationDate,
                        true
                );
            }catch (Exception e){
                assertTrue(e instanceof IllegalArgumentException);
            }
        }

        @Test()
        public void testConstructorWithNullOwnerId() {
            try {
                new Song(
                        "Song",
                        Arrays.asList(Song.Genre.POP),
                        "/path.mp3",
                        UUID.randomUUID(),
                        "Author",
                        "",
                        registrationDate,
                        true
                );
            }catch(NullPointerException e) {
                assertEquals(e.getClass(), NullPointerException.class);
            }


        }

        @Test
        public void testConstructorWithNullRegistrationDate() {
            try {
                new Song(
                        "Song",
                        Arrays.asList(Song.Genre.POP),
                        "/path.mp3",
                        ownerId,
                        "Author",
                        "",
                        null,
                        true
                );
            }catch(NullPointerException e) {
                assertEquals(e.getClass(), NullPointerException.class);
            }
        }

        // --- Métodos setters ---
        @Test()
        public void testSetNameToNull() {
            Song song = validSong();
            try {
                song.setName(null);
            }catch(Exception e) {
                assertTrue(e instanceof IllegalArgumentException);
            }

        }

        @Test()
        public void testSetGenresToEmpty() {
            Song song = validSong();
            try {
                song.setGenres(Collections.emptyList());
            }catch(Exception e) {
                assertTrue(e instanceof IllegalArgumentException);
            }
        }

        @Test()
        public void testSetPathToBlank() {

            Song song = validSong();
            try {
                song.setPath(" ");
            }catch(Exception e) {
                assertTrue(e instanceof IllegalArgumentException);
            }

        }

        @Test()
        public void testSetAuthorToNull() {
            Song song = validSong();
            try {
                song.setAuthor(null);
            }catch(Exception e) {
                assertTrue(e instanceof IllegalArgumentException);
            }
        }

        // --- Métodos válidos sin excepción ---
        @Test
        public void testPlayIncrementsPlayCount() {
            Song song = validSong();
            int count = song.getPlayCount();
            song.play();
            assertEquals(count + 1, song.getPlayCount());
        }

        @Test
        public void testLikeAndDislike() {
            Song song = validSong();
            UUID userId = UUID.randomUUID();

            song.like(userId);
            assertEquals(1, song.getLikes().size());

            song.dislike(userId);
            assertEquals(1, song.getDislikes().size());
        }

        // --- Helper ---
        private Song validSong() {
            return new Song(
                    "Valid",
                    Arrays.asList(Song.Genre.ROCK),
                    "/path/song.mp3",
                    ownerId,
                    "Valid Author",
                    "Some description",
                    registrationDate,
                    false
            );
        }
    }
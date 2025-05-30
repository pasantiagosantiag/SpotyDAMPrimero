package ies.sequeros.dam.spotydam.domain.model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.*;

public class UserTest {

    private LocalDate registrationDate;

    @Before
    public void setUp() {
        registrationDate = LocalDate.now();
    }

    // --- Constructor válido ---
    @Test
    public void testValidUserCreation() {
        User user = new User(
                "Juan Pérez",
                "jperez",
                "avatar.jpg",
                User.Status.ACTIVE,
                registrationDate,
                User.Role.USER
        );

        assertEquals("Juan Pérez", user.getName());
        assertEquals("jperez", user.getNickname());
        assertEquals(User.Status.ACTIVE, user.getStatus());
        assertEquals(User.Role.USER, user.getRole());
        assertNotNull(user.getId());
    }

    // --- Constructor: parámetros inválidos ---
    @Test
    public void testConstructorWithNullStatus() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> {
            new User("Ana", "anita", null, null, registrationDate, User.Role.ADMIN);
        });
        assertEquals("Status cannot be null", ex.getMessage());
    }

    @Test
    public void testConstructorWithNullRegistrationDate() {
        assertThrows(NullPointerException.class, () -> {
            new User("Ana", "anita", null, User.Status.ACTIVE, null, User.Role.ADMIN);
        });
    }

    @Test
    public void testConstructorWithNullRole() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> {
            new User("Ana", "anita", null, User.Status.ACTIVE, registrationDate, null);
        });
        assertEquals("Role cannot be null", ex.getMessage());
    }

    @Test
    public void testConstructorWithBlankName() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new User("   ", "nick", null, User.Status.ACTIVE, registrationDate, User.Role.USER);
        });
        assertEquals("Name cannot be null or blank", ex.getMessage());
    }

    @Test
    public void testConstructorWithNullNickname() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("Valid Name", null, null, User.Status.ACTIVE, registrationDate, User.Role.USER);
        });
    }

    // --- Métodos setName y setNickname ---
    @Test
    public void testSetNameValid() {
        User user = validUser();
        user.setName("Carlos");
        assertEquals("Carlos", user.getName());
    }

    @Test
    public void testSetNameToNull() {
        User user = validUser();
        assertThrows(IllegalArgumentException.class, () -> user.setName(null));
    }

    @Test
    public void testSetNicknameToBlank() {
        User user = validUser();
        assertThrows(IllegalArgumentException.class, () -> user.setNickname("   "));
    }

    // --- Métodos de listas ---
    @Test
    public void testAddOwnPlaylistWithNullThrows() {
        User user = validUser();
        assertThrows(NullPointerException.class, () -> user.addOwnPlaylist(null));
    }

    @Test
    public void testAddOtherPlaylistWithValidId() {
        User user = validUser();
        UUID playlistId = UUID.randomUUID();
        user.addOtherPlaylist(playlistId);
        // No excepción = OK
    }

    @Test
    public void testAddLastPlayedSongAtBeginning() {
        User user = validUser();
        UUID song1 = UUID.randomUUID();
        UUID song2 = UUID.randomUUID();
        user.addLastPlayedSong(song1);
        user.addLastPlayedSong(song2);
        // No getter, se testea por no lanzar excepción
    }

    @Test
    public void testSetPassword() {
        User user = validUser();
        user.setPassword("superSecret");
        assertEquals("superSecret", user.getPassword());
    }

    // --- Helper ---
    private User validUser() {
        return new User("Alice", "ali123", "img.png", User.Status.ACTIVE, registrationDate, User.Role.USER);
    }
}
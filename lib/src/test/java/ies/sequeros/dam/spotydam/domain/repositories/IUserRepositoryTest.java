package ies.sequeros.dam.spotydam.domain.repositories;

import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.infraestructure.files.UserRepositoryInFile;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

import static org.junit.Assert.*;

public class IUserRepositoryTest {
    private IUserRepository repository;
    private User sampleUser;

    @Before
    public void setUp() {
        repository = new UserRepositoryInFile("users.json");
        repository.removeAll();
        sampleUser = new User(
                "John Doe",
                "johnd",
                "image.png",
                User.Status.ACTIVE,
                LocalDate.now(),
                User.Role.USER
        );
        sampleUser.setPassword("secret123");
        sampleUser.setNickname("john@example.com"); // usaremos el nickname como email
    }

    @Test
    public void testAddAndFindByNickAndPassword() throws NoSuchFieldException {
        repository.add(sampleUser);
        User found = repository.findByNickAndPassword("john@example.com", "secret123");
        assertNotNull(found);
        assertEquals(sampleUser.getId(), found.getId());
    }

    @Test
    public void testFindByNickAndPasswordNotFound() {
        User result = repository.findByNickAndPassword("notfound@example.com", "wrong");
        assertNull(result);
    }

    @Test
    public void testDeleteUser() throws NoSuchFieldException {
        repository.add(sampleUser);
        repository.delete(sampleUser);
        assertNull(repository.findByNickAndPassword("john@example.com", "secret123"));
    }



    @Test
    public void testUpdateUser()  {
        repository.add(sampleUser);
        sampleUser.setPassword("newpass");
        repository.update(sampleUser);
        User found = repository.findByNickAndPassword("john@example.com", "newpass");
        assertNotNull(found);
    }

    @Test()
    public void testUpdateNonexistentUser()  {
        assertThrows(IllegalArgumentException.class, ()->{ repository.update(sampleUser);});
    }

    @Test
    public void testFindAll() throws NoSuchFieldException {
        repository.add(sampleUser);
        List<User> users = repository.findAll();
        assertEquals(1, users.size());
    }
}

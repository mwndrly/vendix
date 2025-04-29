package test;
import models.User;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testUserConstructorAndGetters() {
        User user = new User("João", "joao@email.com", "senha123");

        assertEquals("João", user.getName());
        assertEquals("joao@email.com", user.getEmail());
        assertEquals("senha123", user.getPassword());
    }

    @Test
    public void testSetters() {
        User user = new User("João", "joao@email.com", "senha123");
        
        user.setName("Carlos");
        user.setEmail("carlos@email.com");
        user.setPassword("novaSenha");

        assertEquals("Carlos", user.getName());
        assertEquals("carlos@email.com", user.getEmail());
        assertEquals("novaSenha", user.getPassword());
    }
}

package test;

import models.Main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class MainTest {
    private Main main;

    private Main setupWithInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        return new Main(scanner);
    }

    @BeforeEach
    public void clearUsersBeforeEachTest() {
        Main.users.clear();
    }

    @Test
    public void testLoginValidUser() {
        main = setupWithInput("2\nJoão\njoao@email.com\nsenha123\n");
        main.showWelcomeMenu();

        main = setupWithInput("1\njoao@email.com\nsenha123\n");
        main.showWelcomeMenu();

        assertNotNull(main.loggedUser);
        assertEquals("joao@email.com", main.loggedUser.getEmail());
    }

    @Test
    public void testRegisterUserValid() {
        main = setupWithInput("2\nCarlos\ncarlos@email.com\nsenha123\n");
        main.showWelcomeMenu();

        assertTrue(main.users.stream()
            .anyMatch(user -> user.getEmail().equals("carlos@email.com")));
    }

    @Test
    public void testRegisterUserWithShortPassword() {
        main = setupWithInput("2\nCarlos\ncarlos@email.com\n123\n");
        main.showWelcomeMenu();

        assertEquals(0, main.users.size());
    }
}

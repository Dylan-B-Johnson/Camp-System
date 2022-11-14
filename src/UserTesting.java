import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTesting {

    private String email;

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void validEmail() {
        email = "bob@hotmail.com";
        boolean isValid = User.emailIsValid(email);
        assertTrue(isValid);
    }

    @Test
    public void emailIsInvalid() {
        email = "bobhotmailcom";
        boolean isValid = User.emailIsValid(email);
        assertFalse(isValid);
    }

    @Test
    public void emailIsNull() {
        email = null;
        assertNull(email);
    }

}
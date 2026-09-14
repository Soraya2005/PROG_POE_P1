package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class LoginTest {

    @Test
    void testValidUsername() {

        Login login = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(login.checkUserName());
    }

    @Test
    void testInvalidUsername() {

        Login login = new Login(
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(login.checkUserName());
    }

    @Test
    void testValidPassword() {

        Login login = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    void testInvalidPassword() {

        Login login = new Login(
                "kyl_1",
                "password",
                "+27838968976"
        );

        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    void testValidCellPhoneNumber() {

        Login login = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    void testInvalidCellPhoneNumber() {

        Login login = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553"
        );

        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    void testSuccessfulLogin() {

        Login login = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(
                login.loginUser("kyl_1", "Ch&&sec@ke99!")
        );
    }

    @Test
    void testFailedLogin() {

        Login login = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(
                login.loginUser("wrong", "wrong")
        );
    }

    @Test
    void testRegistrationSuccessMessage() {

        Login login = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(
                "User successfully registered.",
                login.registerUser()
        );
    }
}

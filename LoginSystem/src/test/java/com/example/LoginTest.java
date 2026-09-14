package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class LoginTest {

    // Test user (5 parameters, POE data)
    Login user = new Login("aya_1", "Ch&&sec@ke99!", "+27838968976", "Soraya", "Pechera");

    // ===== checkUserName tests =====
    @Test
    void testValidUsername() {
        assertTrue(user.checkUserName("aya_1"));
    }

    @Test
    void testInvalidUsername() {
        assertFalse(user.checkUserName("soraya!!!!!!!"));
    }

    // ===== checkPasswordComplexity tests =====
    @Test
    void testValidPassword() {
        assertTrue(user.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    void testInvalidPassword() {
        assertFalse(user.checkPasswordComplexity("password"));
    }

    // ===== checkCellPhoneNumber tests =====
    @Test
    void testValidCellPhoneNumber() {
        assertTrue(user.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    void testInvalidCellPhoneNumber() {
        assertFalse(user.checkCellPhoneNumber("08966553"));
    }

    // ===== loginUser tests =====
    @Test
    void testSuccessfulLogin() {
        assertTrue(user.loginUser("aya_1", "Ch&&sec@ke99!"));
    }

    @Test
    void testFailedLogin() {
        assertFalse(user.loginUser("wrong", "wrong"));
    }

    // ===== registerUser test  =====
    @Test
    void testRegistrationSuccessMessage() {
        assertEquals(
            "Username successfully captured.\n"
          + "Password successfully captured.\n"
          + "Cell number successfully added.",
            user.registerUser()
        );
    }

    // ===== returnLoginStatus tests  =====
    @Test
    void testReturnLoginStatusSuccess() {
        assertEquals(
            "Welcome Soraya, Pechera it is great to see you again.",
            user.returnLoginStatus(true)
        );
    }

    @Test
    void testReturnLoginStatusFail() {
        assertEquals(
            "Username or password incorrect, please try again.",
            user.returnLoginStatus(false)
        );
    }
}
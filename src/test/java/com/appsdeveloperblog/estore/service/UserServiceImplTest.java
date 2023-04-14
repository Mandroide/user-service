package com.appsdeveloperblog.estore.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
class UserServiceImplTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    void testCreateUser_whenUserDetailsProvided_ReturnUserObject() {
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "janedoe@example.com";
        String password = "TestPassword1+";
        String repeatPassword = "TestPassword1+";
        // Act
        var actual = userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertAll("Valid user object",
                () -> assertNotNull(actual, "The createUser() should not have return null"),
                () -> assertEquals(firstName, actual.getFirstName(), "User's firstName is incorrect"),
                () -> assertEquals(lastName, actual.getLastName(), "User's lastName is incorrect"),
                () -> assertEquals(email, actual.getEmail(), "User's email is incorrect"),
                () -> assertNotNull(actual.getId(), "User id is missing")
        );
    }
}
package com.appsdeveloperblog.estore.service;

import com.appsdeveloperblog.estore.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.Random.class)
class UserValidatorTest {

    @DisplayName("An empty field causes correct exception")
    @CsvFileSource(resources = "user_an_empty_field.csv", useHeadersInDisplayName = true)
    @ParameterizedTest
    void testValidate_whenAFieldIsEmpty_ThrowsIllegalArgumentException(String firstName,
                                                                       String lastName,
                                                                       String email,
                                                                       String password,
                                                                       String repeatPassword,
                                                                       String exceptionMessage) {
        // Arrange
        var user = new User(firstName, lastName, email, password, repeatPassword);

        // Act & Assert
        var thrown = assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validate(user),
                "Empty field should have caused an Illegal Argument Exception");
        assertEquals(exceptionMessage, thrown.getMessage(), "Exception error message is not correct");

    }

    @DisplayName("An invalid firstName causes correct exception")
    @CsvFileSource(resources = "user_invalid_firstName.csv", useHeadersInDisplayName = true)
    @ParameterizedTest
    void testValidate_whenFieldNameIsInvalid_ThrowsIllegalArgumentException(String firstName,
                                                                            String lastName,
                                                                            String email,
                                                                            String password,
                                                                            String repeatPassword,
                                                                            String exceptionMessage) {
        // Arrange
        var user = new User(firstName, lastName, email, password, repeatPassword);

        // Act & Assert
        var thrown = assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validate(user),
                "Invalid firstName field should have caused an Illegal Argument Exception");
        assertEquals(exceptionMessage, thrown.getMessage(), "Exception error message is not correct");

    }

    @Test
    void testValidate_WhenNotBlankPasswordAndNotBlankRepeatPasswordAreDifferent_ThrowsIllegalArgumentException() {
        // Arrange
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "janedoe@example.com";
        String password = "TestPassword1.";
        String repeatPassword = "TestPassword2.";
        String exceptionMessage = "User's password and repeat password do not match";
        var user = new User(firstName, lastName, email, password, repeatPassword);

        // Act & Assert
        var thrown = assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validate(user),
                exceptionMessage);
        assertEquals(exceptionMessage, thrown.getMessage(), "Exception error message is not correct");
    }

    @CsvFileSource(resources = "user_invalid_passwords.csv", useHeadersInDisplayName = true)
    @ParameterizedTest
    void testValidate_PasswordIsInvalid_ThrowsIllegalArgumentException(String firstName,
                                                                       String lastName,
                                                                       String email,
                                                                       String password,
                                                                       String repeatPassword,
                                                                       String exceptionMessage) {
        // Arrange
        var user = new User(firstName, lastName, email, password, repeatPassword);

        // Act & Assert
        var thrown = assertThrows(IllegalArgumentException.class,
                () -> UserValidator.validate(user),
                "Invalid password should have caused an Illegal Argument Exception");
        assertEquals(exceptionMessage, thrown.getMessage(), "Exception error message is not correct");
    }
}
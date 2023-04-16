package com.appsdeveloperblog.estore.service;

import com.appsdeveloperblog.estore.data.UsersRepository;
import com.appsdeveloperblog.estore.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private EmailVerificationServiceImpl emailVerificationService;

    @Test
    void testCreateUser_whenUserDetailsProvided_ReturnUserObject() {
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "janedoe@example.com";
        String password = "TestPassword1+";
        String repeatPassword = "TestPassword1+";

        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);

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
        Mockito.verify(usersRepository)
                .save(any(User.class));
    }

    @DisplayName("If save() method causes RunTimeException, a UserServiceException is thrown")
    @Test
    void testCreateUser_WhenSaveMethodThrowsException_thenTrowsUserServiceException() {
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "janedoe@example.com";
        String password = "TestPassword1+";
        String repeatPassword = "TestPassword1+";

        // Arrange
        when(usersRepository.save(any(User.class))).thenThrow(RuntimeException.class);

        // Act & Assert
        assertThrows(UserServiceException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Should have thrown UserServiceException instead");

    }

    @DisplayName("EmailNotificationException is handled")
    @Test
    void  testCreateUser_whenEmailNotificationExceptionThrown_throwsUserServiceException() {
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "janedoe@example.com";
        String password = "TestPassword1+";
        String repeatPassword = "TestPassword1+";

        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);
        doThrow(EmailNotificationServiceException.class)
                .when(emailVerificationService).scheduleEmailConfirmation(any(User.class));

        // Overwrite doThrow()
//        doNothing().when(emailVerificationService).scheduleEmailConfirmation(any(User.class));

        // Act & Assert
        assertThrows(UserServiceException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Should have thrown UserServiceException instead");

        verify(emailVerificationService).scheduleEmailConfirmation(any(User.class));
    }

    @DisplayName("Schedule Email Confirmation is executed")
    @Test
    void testCreateUser_WhenUserCreated_SchedulesEmailConfirmation() {
        // Arrange
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "janedoe@example.com";
        String password = "TestPassword1+";
        String repeatPassword = "TestPassword1+";
        when(usersRepository.save(any(User.class))).thenReturn(true);

        doCallRealMethod().when(emailVerificationService).scheduleEmailConfirmation(any(User.class));

        // Act
        userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        verify(emailVerificationService).scheduleEmailConfirmation(any(User.class));
    }
}
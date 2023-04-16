package com.appsdeveloperblog.estore.service;

import com.appsdeveloperblog.estore.data.UsersRepository;
import com.appsdeveloperblog.estore.model.User;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final EmailVerificationService emailVerificationService;

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {
        var user = new User(UUID.randomUUID().toString(), firstName, lastName, email, password, repeatPassword);
        UserValidator.validate(user);
        boolean isUserCreated;
        try {
            isUserCreated = usersRepository.save(user);
        } catch (RuntimeException e) {
            throw new UserServiceException(e.getMessage());
        }
        if (!isUserCreated) throw new UserServiceException("Could not create user");

        try {
            emailVerificationService.scheduleEmailConfirmation(user);
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage());
        }
        return user;
    }
}

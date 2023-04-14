package com.appsdeveloperblog.estore.service;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {
        var user = new User(UUID.randomUUID().toString(), firstName, lastName, email, password, repeatPassword);
        UserValidator.validate(user);
        return user;
    }
}

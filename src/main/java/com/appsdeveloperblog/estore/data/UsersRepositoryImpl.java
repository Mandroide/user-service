package com.appsdeveloperblog.estore.data;

import com.appsdeveloperblog.estore.model.User;

import java.util.HashMap;
import java.util.Map;

public class UsersRepositoryImpl implements UsersRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public boolean save(User user) {
        boolean userExists = users.containsKey(user.getId());
        if (userExists) {
            users.put(user.getId(), user);
        }
        return userExists;
    }
}

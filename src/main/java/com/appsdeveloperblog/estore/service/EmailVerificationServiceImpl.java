package com.appsdeveloperblog.estore.service;

import com.appsdeveloperblog.estore.model.User;

public class EmailVerificationServiceImpl implements EmailVerificationService {
    @Override
    public void scheduleEmailConfirmation(User user) {
        // TODO - Put user details into mail queue
        System.out.println(user);
    }
}

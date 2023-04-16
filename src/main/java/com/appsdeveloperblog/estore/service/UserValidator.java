package com.appsdeveloperblog.estore.service;

import com.appsdeveloperblog.estore.model.User;
import org.junit.platform.commons.util.StringUtils;
import org.passay.*;

import java.util.Objects;

public class UserValidator {

    public static void validate(User user) {
        Objects.requireNonNull(user, "user cannot be null");
        String password = user.getPassword();
        String repeatPassword = user.getRepeatPassword();
        if (StringUtils.isBlank(user.getFirstName())) {
            throw new IllegalArgumentException("User's first name is empty");
        } else if (StringUtils.isBlank(user.getLastName())) {
            throw new IllegalArgumentException("User's last name is empty");
        } else if (StringUtils.isBlank(user.getEmail())) {
            throw new IllegalArgumentException("User's email is empty");
        } else if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("User's password is empty");
        } else if (StringUtils.isBlank(repeatPassword)) {
            throw new IllegalArgumentException("User's repeat password is empty");
        } else if (!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("User's password and repeat password do not match");
        }

        RuleResult ruleResult;

        PasswordValidator firstNameValidator = new PasswordValidator(
                new LengthRule(1, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1)
        );

        ruleResult = firstNameValidator.validate(new PasswordData(user.getFirstName()));
        if (!ruleResult.isValid()) {
            throw new IllegalArgumentException("User's first name must contain between 1 and 30 alphabetic characters");
        }

        var passwordValidator = new PasswordValidator(
                new LengthRule(8, Integer.MAX_VALUE),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1)
        );

        ruleResult = passwordValidator.validate(new PasswordData(password));
        if (!ruleResult.isValid()) {
            throw new IllegalArgumentException("User's password must have length of at least 8 characters and contain upper case letters, lower case letters, special characters");
        }
    }
}

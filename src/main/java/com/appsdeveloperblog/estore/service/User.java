package com.appsdeveloperblog.estore.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String repeatPassword;

    public User(String firstName,
                String lastName,
                String email,
                String password,
                String repeatPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }
}

package com.appsdeveloperblog.estore.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package org.springframework.boot.autoconfigure.data.tarantool.user;

import org.springframework.data.tarantool.core.mapping.PrimaryKey;
import org.springframework.data.tarantool.core.mapping.Space;

import java.util.UUID;

@Space("users")
public class User {
    @PrimaryKey
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

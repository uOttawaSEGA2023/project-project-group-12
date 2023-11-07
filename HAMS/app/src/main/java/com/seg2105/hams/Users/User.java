package com.seg2105.hams.Users;

import java.io.Serializable;

public abstract class User implements Serializable {
    private String UUID;
    private String email;

    public User (String UUID, String email) {
        this.UUID = UUID;
        this.email = email;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "UUID: " + UUID + " email: " + email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public abstract String getRole();
    public abstract void setRole();

}

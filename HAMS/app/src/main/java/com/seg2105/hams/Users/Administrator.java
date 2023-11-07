package com.seg2105.hams.Users;

public class Administrator extends User{
    public Administrator() {};
    public Administrator(String UUID, String email) {
        super(UUID, email);
    }

    @Override
    public String getRole() {
        return "admin";
    }

    @Override
    public void setRole() {

    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setUUID(String UUID) {
        super.setUUID(UUID);
    }

    @Override
    public String getUUID() {
        return super.getUUID();
    }
}

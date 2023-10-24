package com.seg2105.hams.Users;

public class Administrator extends User{
    public Administrator(String UUID, String email) {
        super(UUID, email);
    }

    @Override
    public String getRole() {
        return "Admin";
    }


}

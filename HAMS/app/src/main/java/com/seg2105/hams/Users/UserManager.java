package com.seg2105.hams.Users;

import static com.seg2105.hams.Database.FirebaseDB.*;


public class UserManager {
    private static User currentUser;


    public static User getUserFromFirebase(String UUID){
        return deserializeFromFirebase("nodes", "objectId", User.class);
    }
    public static boolean putUserInFirebase(User user) {
        try {
            return serializeToFirebase(user, "users", user.UUID);
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean isLoggedIn() {
        return currentUser==null;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
}

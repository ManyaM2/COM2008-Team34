
public class CurrentUserManager {
    // Static field to hold the current user
    private static User currentUser;

    // Private constructor to enforce singleton pattern
    private CurrentUserManager() {
        // No instances of this class can be created externally
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
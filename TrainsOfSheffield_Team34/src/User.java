import java.util.List;

public class User {
    private int userID;
    private String userEmail;
    private List<String> userRoles;
    private String userForename;
    private String userSurname;

    public int getUserID() {
        return userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public String getUserForename() {
        return userForename;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public User (String forename, String surname, int id, String email, List<String> roles) {
        this.userForename = forename;
        this.userSurname = surname;
        this.userID = id;
        this.userEmail = email;
        this.userRoles = roles;
    }
}

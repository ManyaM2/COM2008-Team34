public class Account {

    private Integer userID;
    private String userEmail;
    private String userRoles;

    public Integer getUserID() {
        return userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserRoles() {
        return userRoles;
    }

    public Account (Integer id, String email, String roles) {
        this.userID = id;
        this.userEmail = email;
        this.userRoles = roles;
    }
}

import java.util.List;
public class Account {

    private Integer userID;
    private String userEmail;
    private List<String> userRoles;

    public Integer getUserID() {
        return userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public Account (Integer id, String email, List<String> roles) {
        this.userID = id;
        this.userEmail = email;
        this.userRoles = roles;
    }
}

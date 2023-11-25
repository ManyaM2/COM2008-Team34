import java.sql.Connection;
import java.sql.SQLException;
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

    public Address getAddress(Connection connection){
        DatabaseOperations dbOps = new DatabaseOperations();
        try {
            return dbOps.getAddress(connection, userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setUserForename(String fn){ userForename = fn; }
    public void setUserSurname(String sn){ userSurname = sn; }
    public void setUserEmail(String e){ userEmail = e; }

    /**
     * Set haveConfirmed = true for the current user
     * @param connection
     */
    public void setConfirmed(Connection connection){
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        try {
            dbUpdateOps.setConfirmed(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the address of the user locally and on the database
     * @param connection
     * @param address
     */
    public void updateAddress(Connection connection, Address address){
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        try {
            dbUpdateOps.updateAddress(connection, address, this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the user's forename and surname
     * @param connection
     */
    public void updatePersonalDetails(Connection connection){
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        try {
            dbUpdateOps.updatePersonalDetails(connection,this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the user's email
     * @param connection
     */
    public void updateEmail(Connection connection){
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        try {
            dbUpdateOps.updateEmail(connection,this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User (String forename, String surname, String email, List<String> roles) {
        this.userForename = forename;
        this.userSurname = surname;
        this.userEmail = email;
        this.userRoles = roles;
    }
}

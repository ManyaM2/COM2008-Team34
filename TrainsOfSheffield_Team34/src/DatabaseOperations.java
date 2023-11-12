import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {

    /**
     * @param connection
     * @param id The userID of the user whose address you are getting
     * @return The address of the user with the corresponding id
     * @throws SQLException
     */
    public Address getAddress(Connection connection, int id) throws SQLException {
        Address address = null;
        ResultSet resultSet = null;
        try {
            // Get all the addresses from the database
            String sqlQuery = "SELECT u.userID, a.houseNumber, a.postcode, a.roadName, a.cityName " +
                    "FROM Users u, Addresses a " +
                    "WHERE u.houseNumber = a.houseNumber " +
                    "AND u.postCode = a.postcode";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Find the address with the corresponding userID
            while (resultSet.next()) {
                int userID = resultSet.getInt(1);
                int houseNumber = resultSet.getInt(2);
                String postcode = resultSet.getString(3);
                String roadName = resultSet.getString(4);
                String cityName = resultSet.getString(5);

                if (userID == id)
                    address = new Address(houseNumber, roadName, cityName, postcode);
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    /**
     * @param connection
     * @param id The userID of the user whose role(s) you are getting
     * @return The role(s) (as a list of strings) of the supplied user, an empty list if the user is role-less
     * @throws SQLException
     */
    public List<String> getRole(Connection connection, int id) throws SQLException {
        List<String> roles = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the roles from the database
            String sqlQuery = "SELECT u.userID, r.roleID, r.roleName " +
                    "FROM Users u, Roles r " +
                    "WHERE u.roleID = r.roleID";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Find the roles with the corresponding userID
            while (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String roleName = resultSet.getString(3);

                if (userID == id)
                    roles.add(roleName);
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }
}

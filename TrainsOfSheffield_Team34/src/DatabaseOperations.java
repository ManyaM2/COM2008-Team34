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
            // Get the user's address from the database
            String sqlQuery = "SELECT u.userID, a.houseNumber, a.postcode, a.roadName, a.cityName " +
                    "FROM Users u, Addresses a " +
                    "WHERE u.houseNumber = a.houseNumber " +
                    "AND u.postCode = a.postcode " +
                    "AND u.userID = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            resultSet = statement.executeQuery(sqlQuery);

            // Save the query results as an address
            if (resultSet.next()) {
                int houseNumber = resultSet.getInt(2);
                String postcode = resultSet.getString(3);
                String roadName = resultSet.getString(4);
                String cityName = resultSet.getString(5);

                address = new Address(houseNumber, roadName, cityName, postcode);
            }
            resultSet.close();
            statement.close();
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
            //Get the user's roles from the database
            String sqlQuery = "SELECT u.userID, r.roleName " +
                    "FROM Users u, Roles r " +
                    "WHERE u.roleID = r.roleID " +
                    "AND u.userID = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            resultSet = statement.executeQuery(sqlQuery);

            // Add them to the list of strings
            while (resultSet.next()) {
                String roleName = resultSet.getString(2);
                roles.add(roleName);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }

    /**
     * Get the userID, forename, surname and email of all the users in the database
     * @param connection
     * @return A list of users (List<User>)
     * @throws SQLException
     */
    public List<User> getUsers(Connection connection) throws SQLException {
        List<User> users = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the users in the database
            String sqlQuery = "SELECT userID, forename, surname, email " +
                    "FROM Users";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Add the users to the list of users
            while (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String forename = resultSet.getString(2);
                String surname = resultSet.getString(3);
                String email = resultSet.getString(4);
                List<String> roles = getRole(connection, userID);

                users.add(new User(forename, surname, userID, email, roles));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * @param connection
     * @return a list of all the orders in the database (returns an empty list if there are no orders)
     * @throws SQLException
     */
    public List<Order> getOrders(Connection connection) throws SQLException {
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the orders from the database
            String sqlQuery = "SELECT o.orderNumber, o.userID, o.orderStatus, o.orderDate" +
                    "FROM Orders o";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Convert the resultSet into a list of orders
            while (resultSet.next()) {
                int orderNumber = resultSet.getInt(1);
                int userID = resultSet.getInt(2);
                OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString(3));
                String orderDate = resultSet.getString(4);
                List<OrderLine> orderlines = getOrderline(connection, orderNumber);
                orders.add(new Order(orderNumber, userID, orderStatus, orderlines));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * @param connection
     * @param orderNum The order containing the orderLines you are getting
     * @return a list of orderlines associated with a specific order
     * @throws SQLException
     */
    public List<OrderLine> getOrderline(Connection connection, int orderNum) throws SQLException {
        List<OrderLine> orderlines = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get the orderlines associated with the order
            String sqlQuery = "SELECT o.lineNumber, o.orderNumber, o.productCode, o.productQuantity, o.lineCost" +
                    "FROM OrderLine o " +
                    "WHERE o.orderNumber = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, orderNum);
            resultSet = statement.executeQuery(sqlQuery);

            // Add the orderlines to the list of orderlines
            while (resultSet.next()) {
                int lineNumber = resultSet.getInt(1);
                String productCode = resultSet.getString(3);
                int productQuantity = resultSet.getInt(4);

                orderlines.add(new OrderLine(productQuantity,lineNumber,productCode));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderlines;
    }

    public void updateBankDetails(Connection connection, BankDetails details) throws SQLException {
        try {
            String sqlQuery = "UPDATE BankDetails" +
                    "SET cardName = ?, cardNumber = ?, holderName = ?, securityCode = ? " +
                    "WHERE bankDetailsID = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, details.getCardName());
            statement.setInt(2, details.getCardNumber());
            statement.setString(3, details.getHolderName());
            statement.setString(4, details.getSecurityCode());
            statement.setInt(5, details.getBankDetailsID());
            statement.executeUpdate(sqlQuery);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

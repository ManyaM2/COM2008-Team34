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
    /* TODO - Need to edit other classes to get working: discussion required
    public List<User> getUsers(Connection connection) throws SQLException {
        List<User> users = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the roles from the database
            String sqlQuery = "SELECT u.userID, u.forename, u.surname, u.email " +
                    "FROM Users u";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Find the roles with the corresponding userID
            while (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String forename = resultSet.getString(2);
                String surname = resultSet.getString(3);
                String email = resultSet.getString(4);

                users.add(new )
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }*/

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
            //Get all the orderlines from the database
            String sqlQuery = "SELECT o.lineNumber, o.orderNumber, o.productCode, o.productQuantity, o.lineCost" +
                    "FROM OrderLine o";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Find the orderlines with the corresponding orderNumber
            while (resultSet.next()) {
                int lineNumber = resultSet.getInt(1);
                int orderNumber = resultSet.getInt(2);
                String productCode = resultSet.getString(3);
                int productQuantity = resultSet.getInt(4);

                if (orderNumber == orderNum)
                    orderlines.add(new OrderLine(productQuantity,lineNumber,productCode));
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderlines;
    }
}

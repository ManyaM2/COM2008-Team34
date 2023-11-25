import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
            resultSet = statement.executeQuery();

            // Save the query results as an address
            if (resultSet.next()) {
                String houseNumber = resultSet.getString(2);
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
            String sqlQuery = "SELECT o.orderNumber, o.userID, o.orderStatus, o.orderDate " +
                    "FROM Orders o";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Convert the resultSet into a list of orders
            while (resultSet.next()) {
                int orderNumber = resultSet.getInt(1);
                int userID = resultSet.getInt(2);
                OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString(3));
                String orderDateString = resultSet.getString(4);
                LocalDate orderDate = LocalDate.of(Integer.parseInt(orderDateString.substring(0,4)),
                        Integer.parseInt(orderDateString.substring(5,7)),
                        Integer.parseInt(orderDateString.substring(8,10)));
                List<OrderLine> orderlines = getOrderline(connection, orderNumber);
                orders.add(new Order(orderNumber, userID, orderStatus, orderlines, orderDate));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getOrders(Connection connection, User user) throws SQLException {
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the orders from the database
            String sqlQuery = "SELECT orderNumber, orderStatus, orderDate " +
                    "FROM Orders WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, user.getUserID());
            resultSet = statement.executeQuery();

            // Convert the resultSet into a list of orders
            while (resultSet.next()) {
                int orderNumber = resultSet.getInt("orderNumber");
                OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("orderStatus").toUpperCase());
                String orderDateString = resultSet.getString("orderDate");
                LocalDate orderDate = LocalDate.of(Integer.parseInt(orderDateString.substring(0,4)),
                        Integer.parseInt(orderDateString.substring(5,7)),
                        Integer.parseInt(orderDateString.substring(8,10)));
                List<OrderLine> orderlines = getOrderline(connection, orderNumber);
                orders.add(new Order(orderNumber, user.getUserID(), orderStatus, orderlines, orderDate));
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
            String sqlQuery = "SELECT o.lineNumber, o.orderNumber, o.productCode, o.productQuantity, o.lineCost " +
                    "FROM OrderLine o " +
                    "WHERE o.orderNumber = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, orderNum);
            resultSet = statement.executeQuery();

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

    public List<Product> getProducts(Connection connection) throws SQLException {
        List<Product> products = new ArrayList<>();
        products.addAll(getTrainSets(connection));
        products.addAll(getTrackPacks(connection));
        products.addAll(getLocomotives(connection));
        products.addAll(getRollingStock(connection));
        products.addAll(getTrackPieces(connection));
        products.addAll(getControllers(connection));
        return products;
    }

    public List<Set> getTrainSets(Connection connection) throws SQLException {
        List<Set> sets = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the orders from the database
            String sqlQuery = "SELECT s.setID, s.productCode, s.controllerType, " +
                    "p.brandName, p.productName, p.retailPrice, p.gauge, p.stockLevel " +
                    "FROM Sets s, Products p " +
                    "WHERE p.productCode = s.productCode " +
                    "AND s.productCode LIKE 'M%'";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                int setID = resultSet.getInt("setID");
                String productCode = resultSet.getString("productCode");
                String controllerType = resultSet.getString("controllerType");
                String brandName  = resultSet.getString("brandName");
                String productName  = resultSet.getString("productName");
                double retailPrice = resultSet.getDouble("retailPrice");
                String gauge  = resultSet.getString("gauge");
                int stockLevel = resultSet.getInt("stockLevel");

                //Get all the products in the set
                List<Product> setContents = new ArrayList<>();
                setContents.addAll(getControllers(connection, productCode));
                setContents.addAll(getLocomotives(connection, productCode));
                setContents.addAll(getRollingStock(connection, productCode));
                setContents.addAll(getTrackPacks(connection, productCode));

                sets.add(new Set(setID, brandName, gauge, productName, retailPrice, stockLevel, setContents,
                        controllerType, productCode));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sets;
    }
    public List<Set> getTrackPacks(Connection connection) throws SQLException {
        List<Set> sets = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the orders from the database
            String sqlQuery = "SELECT s.setID, s.productCode, s.controllerType, " +
                    "p.brandName, p.productName, p.retailPrice, p.gauge, p.stockLevel " +
                    "FROM Sets s, Products p " +
                    "WHERE p.productCode = s.productCode " +
                    "AND s.productCode LIKE 'P%'";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                int setID = resultSet.getInt("setID");
                String productCode = resultSet.getString("productCode");
                String controllerType = resultSet.getString("controllerType");
                String brandName  = resultSet.getString("brandName");
                String productName  = resultSet.getString("productName");
                double retailPrice = resultSet.getDouble("retailPrice");
                String gauge  = resultSet.getString("gauge");
                int stockLevel = resultSet.getInt("stockLevel");

                //Get all the products in the set
                List<Product> setContents = new ArrayList<>();
                setContents.addAll(getTrackPieces(connection, productCode));

                sets.add(new Set(setID, brandName, gauge, productName, retailPrice, stockLevel, setContents,
                        controllerType, productCode));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sets;
    }
    public List<Set> getTrackPacks(Connection connection, String setCode) throws SQLException {
        List<Set> sets = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the orders from the database
            String sqlQuery = "SELECT s.setID, s.productCode, s.controllerType, " +
                    "p.brandName, p.productName, p.retailPrice, p.gauge, p.stockLevel, p.partOfSetCode " +
                    "FROM Sets s, Products p " +
                    "WHERE p.productCode = s.productCode " +
                    "AND s.productCode LIKE 'P%' " +
                    "AND p.partOfSetCode = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, setCode);
            resultSet = statement.executeQuery(sqlQuery);

            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                int setID = resultSet.getInt("setID");
                String productCode = resultSet.getString("productCode");
                String controllerType = resultSet.getString("controllerType");
                String brandName  = resultSet.getString("brandName");
                String productName  = resultSet.getString("productName");
                double retailPrice = resultSet.getDouble("retailPrice");
                String gauge  = resultSet.getString("gauge");
                int stockLevel = resultSet.getInt("stockLevel");

                //Get all the products in the set
                List<Product> setContents = new ArrayList<>();
                setContents.addAll(getTrackPieces(connection, productCode));

                sets.add(new Set(setID, brandName, gauge, productName, retailPrice, stockLevel, setContents,
                        controllerType, productCode));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sets;
    }

    public List<Product> getTrackPieces(Connection connection) throws SQLException {
        List<Product> trackPieces = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the products with a product code starting with 'R'
            String sqlQuery = "SELECT productCode, brandName, productName, retailPrice, gauge, stockLevel " +
                    "FROM Products " +
                    "WHERE productCode LIKE 'R%'";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                String productCode = resultSet.getString("productCode");
                String brandName  = resultSet.getString("brandName");
                String productName  = resultSet.getString("productName");
                double retailPrice = resultSet.getDouble("retailPrice");
                String gauge  = resultSet.getString("gauge");
                int stockLevel = resultSet.getInt("stockLevel");

                trackPieces.add(new Product(productCode, brandName, gauge, productName, retailPrice, stockLevel));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trackPieces;
    }
    public List<Product> getTrackPieces(Connection connection, String setCode) throws SQLException {
        List<Product> trackPieces = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            // Get all the products with a product code starting with 'R'
            String sqlQuery = "SELECT productCode, brandName, productName, retailPrice, gauge, stockLevel, partOfSetCode " +
                    "FROM Products WHERE productCode LIKE 'R%' AND partOfSetCode = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, setCode);
            resultSet = statement.executeQuery();


            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                String productCode = resultSet.getString("productCode");
                String brandName  = resultSet.getString("brandName");
                String productName  = resultSet.getString("productName");
                double retailPrice = resultSet.getDouble("retailPrice");
                String gauge  = resultSet.getString("gauge");
                int stockLevel = resultSet.getInt("stockLevel");

                trackPieces.add(new Product(productCode, brandName, gauge, productName, retailPrice, stockLevel));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trackPieces;
    }

    public List<Controller> getControllers(Connection connection) throws SQLException {
        List<Controller> controllers = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the orders from the database
            String sqlQuery = "SELECT c.productCode, c.typeName, " +
                    "p.brandName, p.productName, p.retailPrice, p.gauge, p.stockLevel " +
                    "FROM Controller c, Products p " +
                    "WHERE p.productCode = c.productCode";

            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                String typeName  = resultSet.getString("typeName");
                String pCode = resultSet.getString("productCode");
                String bName  = resultSet.getString("brandName");
                String pName  = resultSet.getString("productName");
                double rPrice = resultSet.getDouble("retailPrice");
                String gauge  = resultSet.getString("gauge");
                int sLevel = resultSet.getInt("stockLevel");

                controllers.add(new Controller(pCode, bName, gauge, pName, rPrice, sLevel, typeName));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return controllers;
    }
    public List<Controller> getControllers(Connection connection, String setCode) throws SQLException {
        List<Controller> controllers = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the products with the same set code
            String sqlQuery = "SELECT c.productCode, c.typeName, p.partOfSetCode, " +
                    "p.brandName, p.productName, p.retailPrice, p.gauge, p.stockLevel" +
                    "FROM Controller c, Products p " +
                    "WHERE p.productCode = c.productCode " +
                    "AND p.partOfSetCode = ?";

            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, setCode);
            resultSet = statement.executeQuery(sqlQuery);

            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                String typeName  = resultSet.getString("typeName");
                String pCode = resultSet.getString("productCode");
                String bName  = resultSet.getString("brandName");
                String pName  = resultSet.getString("productName");
                double rPrice = resultSet.getDouble("retailPrice");
                String gauge  = resultSet.getString("gauge");
                int sLevel = resultSet.getInt("stockLevel");

                controllers.add(new Controller(pCode, bName, gauge, pName, rPrice, sLevel, typeName));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return controllers;
    }

    public List<Locomotive> getLocomotives(Connection connection) throws SQLException {
        List<Locomotive> locomotives = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the orders from the database
            String sqlQuery = "SELECT l.productCode, l.dccCode, l.eraCode, " +
                    "p.brandName, p.productName, p.retailPrice, p.gauge, p.stockLevel " +
                    "FROM Locomotives l, Products p " +
                    "WHERE p.productCode = l.productCode";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                String dccCode = resultSet.getString("dccCode");
                String eraCode  = resultSet.getString("eraCode");
                String pCode = resultSet.getString("productCode");
                String bName  = resultSet.getString("brandName");
                String pName  = resultSet.getString("productName");
                double rPrice = resultSet.getDouble("retailPrice");
                String gauge  = resultSet.getString("gauge");
                int sLevel = resultSet.getInt("stockLevel");

                locomotives.add(new Locomotive(pCode, bName, gauge, pName, rPrice, sLevel, eraCode, dccCode));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locomotives;
    }
    public List<Locomotive> getLocomotives(Connection connection, String setCode) throws SQLException {
        List<Locomotive> locomotives = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get the locomotives with the same set code
            String sqlQuery = "SELECT l.productCode, l.dccCode, l.eraCode, p.partOfSetCode, " +
                    "p.brandName, p.productName, p.retailPrice, p.gauge, p.stockLevel " +
                    "FROM Locomotives l, Products p " +
                    "WHERE p.productCode = l.productCode " +
                    "AND p.partOfSetCode = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, setCode);
            resultSet = statement.executeQuery(sqlQuery);

            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                String dccCode = resultSet.getString("dccCode");
                String eraCode  = resultSet.getString("eraCode");
                String pCode = resultSet.getString("productCode");
                String bName  = resultSet.getString("brandName");
                String pName  = resultSet.getString("productName");
                double rPrice = resultSet.getDouble("retailPrice");
                String gauge  = resultSet.getString("gauge");
                int sLevel = resultSet.getInt("stockLevel");

                locomotives.add(new Locomotive(pCode, bName, gauge, pName, rPrice, sLevel, eraCode, dccCode));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locomotives;
    }

    public List<RollingStock> getRollingStock(Connection connection) throws SQLException {
        List<RollingStock> rollingStock = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get all the orders from the database
            String sqlQuery = "SELECT rs.productCode, rs.eraCode, " +
                    "p.brandName, p.productName, p.retailPrice, p.gauge, p.stockLevel " +
                    "FROM RollingStock rs, Products p " +
                    "WHERE p.productCode = rs.productCode";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                String eraCode  = resultSet.getString("eraCode");
                String pCode = resultSet.getString("productCode");
                String bName  = resultSet.getString("brandName");
                String pName  = resultSet.getString("productName");
                double rPrice = resultSet.getDouble("retailPrice");
                String gauge  = resultSet.getString("gauge");
                int sLevel = resultSet.getInt("stockLevel");

                rollingStock.add(new RollingStock(pCode, bName, gauge, pName, rPrice, sLevel, eraCode));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rollingStock;
    }

    public List<RollingStock> getRollingStock(Connection connection, String setCode) throws SQLException {
        List<RollingStock> rollingStock = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get the rolling stock with the same set code
            String sqlQuery = "SELECT rs.productCode, rs.eraCode, p.partOfSetCode, " +
                    "p.brandName, p.productName, p.retailPrice, p.gauge, p.stockLevel " +
                    "FROM RollingStock rs, Products p " +
                    "WHERE p.productCode = rs.productCode " +
                    "AND p.partOfSetCode = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, setCode);
            resultSet = statement.executeQuery(sqlQuery);

            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                String eraCode  = resultSet.getString("eraCode");
                String pCode = resultSet.getString("productCode");
                String bName  = resultSet.getString("brandName");
                String pName  = resultSet.getString("productName");
                double rPrice = resultSet.getDouble("retailPrice");
                String gauge  = resultSet.getString("gauge");
                int sLevel = resultSet.getInt("stockLevel");

                rollingStock.add(new RollingStock(pCode, bName, gauge, pName, rPrice, sLevel, eraCode));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rollingStock;
    }

    /**
     * Get a list of integers representing the quanity of products in a set
     * @param connection
     * @param set
     * @return
     * @throws SQLException
     */
    public List<Integer> getComponentsQuantity(Connection connection, Set set) throws SQLException {
        List<Integer> quantities = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            //Get the rolling stock with the same set code
            String sqlQuery = "SELECT quantity FROM ProductsInSet WHERE setCode = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, set.getProductCode());
            resultSet = statement.executeQuery();

            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                int quantity  = resultSet.getInt("quantity");

                quantities.add(quantity);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quantities;
    }

    public boolean checkIfConfirmed(Connection connection) throws SQLException {
        boolean haveConfirmed = false;
        ResultSet resultSet = null;
        try {
            String sqlQuery = "SELECT haveConfirmed FROM Users WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, CurrentUserManager.getCurrentUser().getUserID());
            resultSet = statement.executeQuery();

            // Convert the resultSet into a list of products
            while (resultSet.next()) {
                haveConfirmed = resultSet.getBoolean("haveConfirmed");
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return haveConfirmed;
    }

    /**
     * Verifies the login credentials of a user.
     *
     * @param connection       The database connection.
     * @param username         The entered username.
     * @param enteredPassword  The entered password.
     * @return True if login is successful, false otherwise.
     */
    public boolean verifyLogin(Connection connection, String username, char[] enteredPassword) {
        try {
            // Query the database to fetch user information
            String sql = "SELECT userId, forename, surname, email, password, houseNumber, postCode " +
                    "FROM Users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString("userId");
                String storedPasswordHash = resultSet.getString("password");
                String email = resultSet.getString("email");

                // Verify the entered password against the stored hash
                if (verifyPassword(enteredPassword, storedPasswordHash)) {
                    return true;
                } else {
                    System.out.println("Incorrect password.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User not found.");
        return false;
    }

    /**
     * Verifies a password against a stored hash.
     *
     * @param enteredPassword  The entered password.
     * @param storedPasswordHash  The stored password hash.
     * @return True if the password is verified, false otherwise.
     */
    private static boolean verifyPassword(char[] enteredPassword, String storedPasswordHash) {
        try {
            String hashedEnteredPassword = HashedPasswordGenerator.hashPassword(enteredPassword);
            return hashedEnteredPassword.equals(storedPasswordHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
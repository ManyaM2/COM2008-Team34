import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DbUpdateOperations {

    /**
     * Update the stock level of a product in the database and locally
     * @param connection The database connection
     * @param updateNum The quantity the stock is being updated by (3, -2 etc.)
     * @param product The product being updated
     * @throws SQLException
     */
    public void updateStockLevel(Connection connection, int updateNum, Product product) throws SQLException {
        try {
            product.updateStock(updateNum);
            String sqlQuery = "UPDATE Products SET stockLevel = ? WHERE productCode = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, product.getStockLevel());
            statement.setString(2, product.getProductCode());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrderLine(Connection connection, Product p) throws SQLException{
        ResultSet resultSet = null;
        try {
            resultSet = getPendingOrders(connection);

            //If there is a pending order, add an orderline to the order
            if (resultSet.next()){
                int orderNumber = -1;
                orderNumber = resultSet.getInt("orderNumber");

                String updateQuery = "INSERT INTO OrderLine (orderNumber, productCode, productQuantity, lineCost) " +
                        "Values(?,?,?,?)";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setInt(1, orderNumber);
                updateStatement.setString(2, p.getProductCode());
                updateStatement.setInt(3, 1);
                updateStatement.setDouble(4, p.getRetailPrice());
                updateStatement.executeUpdate();
            } //Otherwise, make a new order
            else {
                String insertOrder = "INSERT INTO Orders (userID, orderStatus, orderDate) Values(?,?,?)";
                PreparedStatement orderStatement = connection.prepareStatement(insertOrder);
                orderStatement.setInt(1, CurrentUserManager.getCurrentUser().getUserID());
                orderStatement.setString(2, "Pending");
                orderStatement.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
                orderStatement.executeUpdate();

                resultSet = getPendingOrders(connection);

                int orderNumber = -1;
                while (resultSet.next())
                    orderNumber = resultSet.getInt("orderNumber");

                String updateQuery = "INSERT INTO OrderLine (orderNumber, productCode, productQuantity, lineCost) " +
                        "Values(?,?,?,?)";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setInt(1, orderNumber);
                updateStatement.setString(2, p.getProductCode());
                updateStatement.setInt(3, 1);
                updateStatement.setDouble(4, p.getRetailPrice());
                updateStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeOrderLine(Connection connection, OrderLine ol) throws SQLException{
        try {
            String updateQuery = "DELETE FROM OrderLine WHERE lineNumber = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, ol.getLineNumber());
            updateStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the quantity of an orderline (sets productQuantity to ol.getProductQuantity())
     * @param connection
     * @param ol
     * @throws SQLException
     */
    public void updateOrderLineQuantity(Connection connection, OrderLine ol) throws SQLException{
        try {
            String sqlQuery = "UPDATE OrderLine SET productQuantity = ? WHERE lineNumber = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, ol.getProductQuantity());
            statement.setInt(2, ol.getLineNumber());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the status of an order to the status stored in the order instance
     * @param connection
     * @param o the order instance
     * @throws SQLException
     */
    public void updateOrderStatus(Connection connection, Order o) throws SQLException{
        try{
            String sqlQuery = "UPDATE Orders SET orderStatus = ? WHERE orderNumber = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            String status = o.getStatus().toString().toLowerCase();
            status = Character.toUpperCase(status.charAt(0)) + status.substring(1);
            statement.setString(1, status);
            statement.setInt(2, o.getOrderNumber());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set 'haveConfirmed' = true for the current user
     * @param connection
     * @throws SQLException
     */
    public void setConfirmed(Connection connection) throws SQLException{
        try{
            String sqlQuery = "UPDATE Users SET haveConfirmed = TRUE WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, CurrentUserManager.getCurrentUser().getUserID());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a user's bank details to the database, if they already have details stored,
     * the previous details are deleted
     * @param connection
     * @param bd The bank details
     * @throws SQLException
     */
    public void addBankingDetails(Connection connection, BankDetails bd) throws SQLException {
        ResultSet resultSet = null;
        try{
            // Delete a users bank details if they already exist
            String sqlQuery = "SELECT userID FROM BankDetails WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, CurrentUserManager.getCurrentUser().getUserID());
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String deleteQuery = "DELETE FROM BankDetails WHERE userID = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, CurrentUserManager.getCurrentUser().getUserID());
                deleteStatement.executeUpdate();
            }

            // Insert new details
            String updateQuery = "INSERT INTO BankDetails (userID, cardName, cardNumber, expiryDate, holderName, " +
                    "securityCode) Values(?,?,?,?,?,?)";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

            // Ensure card details are hashed before inserting them
            String hashedCardNumber = HashedPasswordGenerator.hashPassword(bd.getCardNumber().toCharArray());
            String hashedExpDate = HashedPasswordGenerator.hashPassword(bd.getExpiryDate().toCharArray());
            String hashedSecCode = HashedPasswordGenerator.hashPassword(bd.getSecurityCode().toCharArray());

            updateStatement.setInt(1, CurrentUserManager.getCurrentUser().getUserID());
            updateStatement.setString(2, bd.getCardName());
            updateStatement.setString(3, hashedCardNumber);
            updateStatement.setString(4, hashedExpDate);
            updateStatement.setString(5, bd.getHolderName());
            updateStatement.setString(6, hashedSecCode);
            updateStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the address of a user
     * @param connection
     * @param address The updated address
     * @throws SQLException
     */
    public void updateAddress(Connection connection, Address address, User user) throws SQLException {
        try{
            // Try to get the address from the database
            String addressQuery = "SELECT houseNumber FROM Addresses WHERE houseNumber = ? AND postcode = ?";
            PreparedStatement addressStatement = connection.prepareStatement(addressQuery);
            addressStatement.setString(1, address.getHouseNumber());
            addressStatement.setString(2, address.getPostCode());
            ResultSet resultSet = addressStatement.executeQuery();

            // If the address doesn't exist, add it to the database
            if (!resultSet.next()) {
                addressQuery = "INSERT INTO Addresses VALUES (?,?,?,?)";
                addressStatement = connection.prepareStatement(addressQuery);
                addressStatement.setString(1, address.getHouseNumber());
                addressStatement.setString(2, address.getPostCode());
                addressStatement.setString(3, address.getRoadName());
                addressStatement.setString(4, address.getCityName());
                addressStatement.executeUpdate();
            }

            // Update the user's address
            String userQuery = "UPDATE Users SET postCode = ?, houseNumber = ? WHERE userID = ?";
            PreparedStatement userStatement = connection.prepareStatement(userQuery);
            userStatement.setString(1, address.getPostCode());
            userStatement.setString(2, address.getHouseNumber());
            userStatement.setInt(3, user.getUserID());
            userStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAddress(Connection connection, Address address) throws SQLException {
        try{
            // Try to get the address from the database
            String addressQuery = "SELECT houseNumber FROM Addresses WHERE houseNumber = ? AND postcode = ?";
            PreparedStatement addressStatement = connection.prepareStatement(addressQuery);
            addressStatement.setString(1, address.getHouseNumber());
            addressStatement.setString(2, address.getPostCode());
            ResultSet resultSet = addressStatement.executeQuery();

            // If the address doesn't exist, add it to the database
            if (!resultSet.next()) {
                addressQuery = "INSERT INTO Addresses VALUES (?,?,?,?)";
                addressStatement = connection.prepareStatement(addressQuery);
                addressStatement.setString(1, address.getHouseNumber());
                addressStatement.setString(2, address.getPostCode());
                addressStatement.setString(3, address.getRoadName());
                addressStatement.setString(4, address.getCityName());
                addressStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(Connection connection, User user, String password, Address address) throws SQLException {
        try{
            String updateQuery = "INSERT INTO Users (forename, surname, email, password, houseNumber, postCode, " +
                    "haveConfirmed) Values(?,?,?,?,?,?,?)";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

            // Ensure card details are hashed before inserting them
            String hashedPassword = HashedPasswordGenerator.hashPassword(password.toCharArray());

            updateStatement.setString(1, user.getUserForename());
            updateStatement.setString(2, user.getUserSurname());
            updateStatement.setString(3, user.getUserEmail());
            updateStatement.setString(4, hashedPassword);
            updateStatement.setString(5, address.getHouseNumber());
            updateStatement.setString(6, address.getPostCode());
            updateStatement.setBoolean(7, false);
            updateStatement.executeUpdate();

            String selectQuery = "SELECT userID FROM Users WHERE email = ? AND password = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, user.getUserEmail());
            selectStatement.setString(2, hashedPassword);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()){
                updateQuery = "INSERT INTO Roles Values(?,?)";
                PreparedStatement newStatement = connection.prepareStatement(updateQuery);
                newStatement.setInt(1, resultSet.getInt("userID"));
                newStatement.setString(2, "Customer");
                newStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update a user's forename and surname
     * @param connection
     * @param user
     * @throws SQLException
     */
    public void updatePersonalDetails(Connection connection, User user) throws SQLException {
        try{
            String userQuery = "UPDATE Users SET forename = ?, surname = ? WHERE userID = ?";
            PreparedStatement userStatement = connection.prepareStatement(userQuery);
            userStatement.setString(1, user.getUserForename());
            userStatement.setString(2, user.getUserSurname());
            userStatement.setInt(3, user.getUserID());
            userStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update a user's email
     * @param connection
     * @param user
     * @throws SQLException
     */
    public void updateEmail(Connection connection, User user) throws SQLException {
        try{
            String userQuery = "UPDATE Users SET email = ? WHERE userID = ?";
            PreparedStatement userStatement = connection.prepareStatement(userQuery);
            userStatement.setString(1, user.getUserEmail());
            userStatement.setInt(2, user.getUserID());
            userStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Get the pending orders of the current user
    private ResultSet getPendingOrders(Connection connection) {
        ResultSet resultSet = null;
        try {
            // Get the user's pending orders
            String sqlQuery = "SELECT orderNumber, orderStatus FROM Orders WHERE userID = ? AND orderStatus = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, CurrentUserManager.getCurrentUser().getUserID());
            statement.setString(2, "Pending");
            resultSet = statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    public void editProductDetails(Connection connection, Product product) throws SQLException{
        try{
            System.out.println(product.getProductName());
            String sqlQuery = "UPDATE Products " +
                    "SET brandName = ?, productName = ?, retailPrice = ?, gauge = ?, stockLevel = ? "
                    + "WHERE productCode = ?";
            PreparedStatement statement =  connection.prepareStatement(sqlQuery);
            statement.setString(1, product.getBrandName());
            statement.setString(2, product.getProductName());
            statement.setDouble(3, product.getRetailPrice());
            statement.setString(4, product.getGauge());
            statement.setInt(5, product.getStockLevel());
            statement.setString(6, product.getProductCode());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduct(Connection connection, Product product) throws SQLException{
        try{
            String sqlQuery = "INSERT INTO Products (productCode, brandName, productName, " +
                    "retailPrice, gauge, stockLevel) VALUES (?, ?, ?, ?, ?, ?) ";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, product.getProductCode());
            statement.setString(2, product.getBrandName());
            statement.setString(3, product.getProductName());
            statement.setDouble(4, product.getRetailPrice());
            statement.setString(5, product.getGauge());
            statement.setInt(6, product.getStockLevel());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addLocomotive(Connection connection, Locomotive locomotive) throws  SQLException{
        try{
            addProduct(connection, locomotive);
            String addQuery = "INSERT INTO Locomotives (productCode, dccCode, eraCode) VALUES (?, ?, ?) ";
            PreparedStatement statement = connection.prepareStatement(addQuery);
            statement.setString(1, locomotive.getProductCode());
            statement.setString(2, locomotive.getDccCode());
            statement.setString(3, locomotive.getEraCode());
            statement.executeUpdate();

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addController(Connection connection, Controller controller) throws  SQLException{
        try{
            addProduct(connection, controller);
            String addQuery = "INSERT INTO Controller(productCode, typeName) VALUES (?, ?) ";
            PreparedStatement statement = connection.prepareStatement(addQuery);
            statement.setString(1, controller.getProductCode());
            statement.setString(2, controller.getTypeName());
            statement.executeUpdate();

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRollingStcok(Connection connection, RollingStock rollingStock) throws  SQLException{
        try{
            addProduct(connection, rollingStock);
            String addQuery = "INSERT INTO Rolling Stock (productCode, eraCode) VALUES (?, ?) ";
            PreparedStatement statement = connection.prepareStatement(addQuery);
            statement.setString(1, rollingStock.getProductCode());
            statement.setString(2, rollingStock.getEraCode());
            statement.executeUpdate();

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTrainSet(Connection connection, Set trainSet) throws  SQLException{
        try{
            addProduct(connection, trainSet);
            String addQuery = "INSERT INTO Rolling Stock (productCode, eraCode, controllerType) VALUES (?, ?, ?) ";
            PreparedStatement statement = connection.prepareStatement(addQuery);
            statement.setString(1, trainSet.getProductCode());
            statement.setString(2, trainSet.getEra());
            statement.setString(3, trainSet.getControllerType());
            statement.executeUpdate();

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToSet(Connection connection, Product product, String setCode) throws  SQLException{
        try{
            String sqlQuery = "SELECT productCode FROM Products WHERE productCode = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, setCode);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                String newQuery = "INSERT INTO ProductsInSet (productCode, setCode, quantity) VALUES (?, ?, ?)";
                PreparedStatement newStatement = connection.prepareStatement(newQuery);
                newStatement.setString(1, product.getProductCode());
                newStatement.setString(2, setCode);
                newStatement.setInt(3, 1);
                newStatement.executeUpdate();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a customer to staff
     * @param connection
     * @param email
     * @return True if the customer exists and is not staff already, false otherwise
     */
    public boolean addToStaff(Connection connection, String email) {
        // Query the database to fetch user information
        try {
            // Check a user with the email address exists
            String sql = "SELECT userID FROM Users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userID = resultSet.getInt("userID");
                // Check the user is not already staff
                String staffSql = "SELECT userID FROM Roles WHERE roleName = ? AND userID = ?";
                PreparedStatement staffStatement = connection.prepareStatement(staffSql);
                staffStatement.setString(1, "Staff");
                staffStatement.setInt(2, userID);
                ResultSet staffResultSet = staffStatement.executeQuery();

                if (staffResultSet.next()){
                    return false;
                } else {
                    // Give the user a staff role
                    String insertQuery = "INSERT INTO Roles VALUES (?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                    insertStatement.setInt(1, userID);
                    insertStatement.setString(2, "Staff");
                    insertStatement.executeUpdate();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void removeFromStaff(Connection connection, User user) {
        // Query the database to fetch user information
        try {
            String insertQuery = "DELETE FROM Roles WHERE roleName = ? AND userID = ?";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, "Staff");
            insertStatement.setInt(2, user.getUserID());
            insertStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Connection connection, Product product) throws SQLException{
        try{
            String setQuery = "DELETE FROM ProductsInSet WHERE productCode = ?";
            PreparedStatement delStatement = connection.prepareStatement(setQuery);
            delStatement.setString(1, product.getProductCode());
            delStatement.executeUpdate();

            String orderLineQuery = "DELETE FROM OrderLine WHERE productCode = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(orderLineQuery);
            deleteStatement.setString(1, product.getProductCode());
            deleteStatement.executeUpdate();

            String locQuery = "DELETE FROM Locomotives WHERE productCode = ?";
            PreparedStatement locStatement = connection.prepareStatement(locQuery);
            locStatement.setString(1, product.getProductCode());
            locStatement.executeUpdate();

            String conQuery = "DELETE FROM Controller WHERE productCode = ?";
            PreparedStatement conStatement = connection.prepareStatement(conQuery);
            conStatement.setString(1, product.getProductCode());
            conStatement.executeUpdate();

            String rolQuery = "DELETE FROM RollingStock WHERE productCode = ?";
            PreparedStatement rolStatement = connection.prepareStatement(rolQuery);
            rolStatement.setString(1, product.getProductCode());
            rolStatement.executeUpdate();

            String sQuery = "DELETE FROM Sets WHERE productCode = ?";
            PreparedStatement setStatement = connection.prepareStatement(sQuery);
            setStatement.setString(1, product.getProductCode());
            setStatement.executeUpdate();

            String sqlQuery = "DELETE FROM Products WHERE productCode = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, product.getProductCode());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

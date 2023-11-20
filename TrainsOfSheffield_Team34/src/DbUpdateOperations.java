import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DbUpdateOperations {
    public void updateBankDetails(Connection connection, BankDetails details) throws SQLException {
        try {
            String sqlQuery = "UPDATE BankDetails " +
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
}

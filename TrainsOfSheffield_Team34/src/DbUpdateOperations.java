import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void addOrderline(Connection connection, Product p) throws SQLException{
        ResultSet resultSet = null;
        try {
            //Get the user's pending orders
            String sqlQuery = "SELECT orderNumber, userID, orderStatus, orderDate " +
                    "FROM Orders " +
                    "WHERE userID = ? " +
                    "AND orderStatus = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            //statement.setInt(1, CurrentUser.getUserID());
            statement.setString(2, "Pending");
            resultSet = statement.executeQuery(sqlQuery);

            //If there are pending orders, add an orderline to the order
            if (resultSet.next()){
                int orderNumber = resultSet.getInt("orderNumber");
                /*
                String updateQuery = "INSERT INTO OrderLines Values(?,?,?,?,?)";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                statement.setInt(1, product.getStockLevel());
                statement.setString(2, product.getProductCode());
                statement.executeUpdate();*/

            } //Otherwise, make a new order
            else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

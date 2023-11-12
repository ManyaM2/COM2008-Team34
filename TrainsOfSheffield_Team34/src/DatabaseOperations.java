package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseOperations {
    public ResultSet getAddresses(Connection connection) throws SQLException {
        List<Adress> adresses = new List<Adress>
        ResultSet resultSet = null;
        try {
            String sqlQuery = "SELECT u.userID, a.houseNumber, a.postcode, a.roadName, a.cityName " +
                    "FROM Users u, Addresses a " +
                    "WHERE u.houseNumber = a.houseNumber " +
                    "AND u.postCode = a.postcode";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                tabelModel.addRow(new Object[]{
                        resultSet.getInt("memberID"),
                        resultSet.getString("forename"),
                        resultSet.getString("surname"),
                        resultSet.getString("title"),
                        resultSet.getString("isbn"),
                        resultSet.getString("copyID"),
                        resultSet.getDate("dueDate")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}

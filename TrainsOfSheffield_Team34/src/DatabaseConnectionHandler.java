import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnectionHandler {
    private static final String DB_URL = "jdbc:mysql://stusql.dcs.shef.ac.uk:3306/team034";
    private static final String DB_USER = "team034";
    private static final String DB_PASSWORD = "yieC6Eipa";
    private Connection connection = null;

    public DatabaseConnectionHandler() {
    }

    public void openConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk:3306/team034", "team034", "yieC6Eipa");
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

    }
    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }

    }

    public Connection getConnection() {
        return this.connection;
    }
}


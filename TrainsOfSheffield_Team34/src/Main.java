
import javax.swing.*;


public class Main {
    public static void main(String[] args){
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // Create and initialize the LoanTableDisplay view using the database connection
                loginView = new LoginView(databaseConnectionHandler.getConnection());
                //staffView = new StaffView(databaseConnectionHandler.getConnection(), testUser);
                loginView.setVisible(true);

            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}

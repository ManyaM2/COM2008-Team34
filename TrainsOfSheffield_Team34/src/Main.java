
import javax.swing.*;


public class Main {
    public static void main(String[] args){
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            WelcomeView welcomeView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // Create and initialize the LoanTableDisplay view using the database connection
                welcomeView = new WelcomeView(databaseConnectionHandler.getConnection());
                //staffView = new StaffView(databaseConnectionHandler.getConnection(), testUser);
                welcomeView.setVisible(true);

            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}

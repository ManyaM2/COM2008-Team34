
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args){
        List<String> testRoles = new ArrayList<>();
        testRoles.add("customer");
        testRoles.add("staff");
        User testUser = new User("Yoongi", "Min", 2, "agustd@bts.com", testRoles);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        CurrentUserManager.setCurrentUser(testUser);

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            DefaultView defaultView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // Create and initialize the LoanTableDisplay view using the database connection
                defaultView = new DefaultView(databaseConnectionHandler.getConnection());
                defaultView.setVisible(true);

            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}

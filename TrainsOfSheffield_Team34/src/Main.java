
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args){
        List<String> testRoles = new ArrayList<>();
        testRoles.add("customer");
        testRoles.add("staff");
        User testUser = new User("Park", "Jimin", 473892, "parkJimin@bts.com", testRoles);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            DefaultView defaultView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // Create and initialize the LoanTableDisplay view using the database connection
                defaultView = new DefaultView(databaseConnectionHandler.getConnection(), testUser);
                defaultView.setVisible(true);

            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}

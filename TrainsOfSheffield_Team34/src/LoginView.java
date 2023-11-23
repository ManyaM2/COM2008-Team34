import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class LoginView extends JFrame {

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton loginButton;
    private JButton cancelButton;

    public LoginView(Connection connection) throws HeadlessException {
        this.setTitle("Trains of Sheffield | Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize.width / 2, screenSize.height / 2);
        setLocation(screenSize.width / 4, screenSize.height / 4);

        // construct components
        usernameLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        usernameField = new JTextField(15);
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");

        // adjust size and set layout
        setPreferredSize(new Dimension(752, 431));
        setLayout(null);

        // add components
        this.add(usernameLabel);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(usernameField);
        this.add(loginButton);
        this.add(cancelButton);

        // set component bounds
        usernameLabel.setBounds(245, 125, 75, 25);
        passwordLabel.setBounds(220, 165, 75, 25);
        passwordField.setBounds(290, 165, 250, 25);
        usernameField.setBounds(290, 125, 250, 25);
        loginButton.setBounds(330, 205, 80, 30);
        cancelButton.setBounds(420, 205, 80, 30);

        // Create an ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                System.out.println(username);
                System.out.println(new String(passwordChars));
                DatabaseOperations databaseOperations = new DatabaseOperations();
                // Check if login is successful
                if (databaseOperations.verifyLogin(connection, username, passwordChars)) {

                    // Secure disposal of the password
                    Arrays.fill(passwordChars, '\u0000');

                    //Show a successful login message
                    JOptionPane.showMessageDialog(LoginView.this, "Welcome user " + username, "Login Successful", JOptionPane.INFORMATION_MESSAGE);

                    // Close the current window
                    dispose();

                    DefaultView defaultView = null;
                    try {
                        defaultView = new DefaultView(connection);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    defaultView.setVisible(true);

                } else {
                    // Show an unsuccessful login message
                    int option = JOptionPane.showConfirmDialog(LoginView.this,
                            "Invalid username or password. Do you want to create a new account?",
                            "Login Failed", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    // Redirect to the sign-up screen
                    if (option == JOptionPane.YES_OPTION) {

                        // Close the current window
                        dispose();

                        /*
                        SignupView signupView = null;
                        try {
                            signupView = new SignupView(connection);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        signupView.setVisible(true);
                         */


                    } else {
                        // Secure disposal of the password
                        Arrays.fill(passwordChars, '\u0000');
                    }
                }
                // Secure disposal of the password
                Arrays.fill(passwordChars, '\u0000');
            }
        });

        // Create an ActionListener for the cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text in the username and password fields
                usernameField.setText("");
                passwordField.setText("");
            }
        });


        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
                new LoginView(databaseConnectionHandler.getConnection());
            }
        });
    }


}
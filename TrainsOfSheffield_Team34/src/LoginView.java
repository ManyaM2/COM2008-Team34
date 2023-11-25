import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class LoginView extends JFrame {

    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton clearButton;

    public LoginView(Connection connection) throws HeadlessException {
        this.setTitle("Trains of Sheffield | Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize.width / 2, screenSize.height / 2);
        setLocation(screenSize.width / 4, screenSize.height / 4);

        // construct components
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");
        emailField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        clearButton = new JButton("Clear");

        // adjust size and set layout
        setPreferredSize(new Dimension(752, 431));
        setLayout(null);

        // add components
        this.add(emailLabel);
        this.add(passwordLabel);
        this.add(emailField);
        this.add(passwordField);
        this.add(loginButton);
        this.add(clearButton);

        // set component bounds
        emailLabel.setBounds(245, 125, 75, 25);
        passwordLabel.setBounds(220, 165, 75, 25);
        emailField.setBounds(290, 125, 250, 25);
        passwordField.setBounds(290, 165, 250, 25);
        loginButton.setBounds(330, 205, 80, 30);
        clearButton.setBounds(420, 205, 80, 30);

        // Create an ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = emailField.getText();
                char[] passwordChars = passwordField.getPassword();
                DatabaseOperations databaseOperations = new DatabaseOperations();

                // Check if login is successful
                if (databaseOperations.verifyLogin(connection, username, passwordChars)) {

                    CurrentUserManager.setCurrentUser(databaseOperations.getUser(connection, username));

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

                        SignupView signupView = null;
                        try {
                            signupView = new SignupView(connection);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        signupView.setVisible(true);

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
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text in the username and password fields
                emailField.setText("");
                passwordField.setText("");
            }
        });

        this.setVisible(true);
    }

}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SignupView extends JFrame {

    private JFrame frame;
    private Address userAddress;

    private JLabel forenameLabel;
    private JTextField forenameField;
    private JLabel surnameLabel;
    private JTextField surnameField;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton signupButton;
    private JButton clearButton;
    private JButton backButton;


    public SignupView(Connection connection) throws HeadlessException {

        frame = this;
        getContentPane().setBackground(new Color(232, 244, 248));

        this.setTitle("Trains of Sheffield | Sign Up");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize.width / 2, screenSize.height / 2);
        setLocation(screenSize.width / 4, screenSize.height / 4);

        // construct components
        //labels
        forenameLabel = new JLabel("First Name:");
        surnameLabel = new JLabel("Surname:");
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");
        //fields
        forenameField = new JTextField(15);
        surnameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        emailField = new JTextField(15);
        //buttons
        signupButton = new JButton("Signup");
        clearButton = new JButton("Clear");
        backButton = new JButton("Back");

        // adjust size and set layout
        setPreferredSize(new Dimension(752, 431));
        setLayout(null);

        // add components
        //labels
        this.add(forenameLabel);
        this.add(surnameLabel);
        this.add(emailLabel);
        this.add(passwordLabel);
        //fields
        this.add(forenameField);
        this.add(surnameField);
        this.add(emailField);
        this.add(passwordField);
        // buttons
        this.add(signupButton);
        this.add(clearButton);
        this.add(backButton);

        // set component bounds
        //labels
        forenameLabel.setBounds(215, 85, 75, 25);
        surnameLabel.setBounds(225, 125, 75, 25);
        emailLabel.setBounds(245, 165, 75, 25);
        passwordLabel.setBounds(220, 205, 75, 25);
        //fields
        forenameField.setBounds(290, 85, 250, 25);
        surnameField.setBounds(290, 125, 250, 25);
        emailField.setBounds(290, 165, 250, 25);
        passwordField.setBounds(290, 205, 250, 25);
        //buttons
        signupButton.setBounds(330, 245, 80, 30);
        clearButton.setBounds(420, 245, 80, 30);
        backButton.setBounds(20,20,70,30);

        // Create an ActionListener for the signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String forename = forenameField.getText();
                String surname = surnameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();

                // Ensure there is text in all the fields
                if (forename.isBlank() || surname.isBlank() || email.isBlank() || password.isBlank()) {
                    JOptionPane.showMessageDialog(frame,"Sign up failed: Please fill in all the fields ",
                            "Invalid Entry", 0);
                } else {
                    // Ensure the email contains the correct characters
                    if (!email.contains("@") || !email.contains(".")){
                        JOptionPane.showMessageDialog(frame, "Sign up failed: Invalid email address",
                                "Invalid Entry", 0);
                    } else { // Apply changes
                        List<String> roles = new ArrayList<>();
                        roles.add("customer");
                        User user = new User(forename, surname, email, roles);

                        addAddress(connection);
                        // Update user details in the database
                        DbUpdateOperations dbups = new DbUpdateOperations();
                        try {
                            dbups.addUser(connection, user, password, userAddress);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        // Update user details in the session
                        CurrentUserManager.setCurrentUser(user);

                        JOptionPane.showMessageDialog(frame, "Sign Up Successful");

                        // Close the signup window and take the user to the login page
                        dispose();

                        LoginView loginView = null;
                        try {
                            loginView = new LoginView(connection);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        loginView.setVisible(true);
                    }
                }

            }
        });

        // Create an ActionListener for the clear button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text in the username and password fields
                forenameField.setText("");
                surnameField.setText("");
                emailField.setText("");
                passwordField.setText("");
            }
        });

        // Create an ActionListener for the back button to go to the Welcome page
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                dispose();

                WelcomeView welcomeView = null;
                try {
                    welcomeView = new WelcomeView(connection);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                welcomeView.setVisible(true);
            }
        });

        this.setVisible(true);
    }

    private void addAddress(Connection connection) {
        DbUpdateOperations dbups = new DbUpdateOperations();

        //Add fields and labels to the form
        JTextField houseNumberField = new JTextField("");
        JTextField postcodeField = new JTextField("");
        JTextField roadNameField = new JTextField("");
        JTextField cityNameField = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("House number: "));
        panel.add(houseNumberField);
        panel.add(new JLabel("Postcode: "));
        panel.add(postcodeField);
        panel.add(new JLabel("Road name: "));
        panel.add(roadNameField);
        panel.add(new JLabel("City name: "));
        panel.add(cityNameField);

        // Process results
        int result = JOptionPane.showConfirmDialog(null, panel, "Edit address",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Ensure all the fields have been filled
            if (houseNumberField.getText().isBlank() || postcodeField.getText().isBlank() ||
                    roadNameField.getText().isBlank() || cityNameField.getText().isBlank()){
                JOptionPane.showMessageDialog(panel,"Please fill in all the fields ",
                        "Invalid Entry", 0);
            } else {
                Address newAddress = new Address(houseNumberField.getText(), roadNameField.getText(),
                        cityNameField.getText(), postcodeField.getText());
                userAddress = newAddress;
                // Update address in database
                try {
                    dbups.addAddress(connection, newAddress);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(panel, "Address updated");
            }
        }
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class SignupView extends JFrame {

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton signupButton;
    private JButton cancelButton;

    public SignupView(Connection connection) throws HeadlessException {
        this.setTitle("Trains of Sheffield | Signup");
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
        signupButton = new JButton("Signup");
        cancelButton = new JButton("Cancel");

        // adjust size and set layout
        setPreferredSize(new Dimension(752, 431));
        setLayout(null);

        // add components
        this.add(usernameLabel);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(usernameField);
        this.add(signupButton);
        this.add(cancelButton);

        // set component bounds
        usernameLabel.setBounds(245, 125, 75, 25);
        passwordLabel.setBounds(220, 165, 75, 25);
        passwordField.setBounds(290, 165, 250, 25);
        usernameField.setBounds(290, 125, 250, 25);
        signupButton.setBounds(330, 205, 80, 30);
        cancelButton.setBounds(420, 205, 80, 30);

        // Create an ActionListener for the signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
}
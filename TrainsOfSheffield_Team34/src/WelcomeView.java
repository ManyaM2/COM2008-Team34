import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class WelcomeView extends JFrame {

    private JButton loginButton;
    private JButton signupButton;
    private JLabel welcomeMessage;
    private JLabel loginMessage;
    private JLabel signupMessage;


    public WelcomeView(Connection connection) throws HeadlessException {
        this.setTitle("Trains of Sheffield | Welcome");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize.width / 2, screenSize.height / 2);
        setLocation(screenSize.width / 4, screenSize.height / 4);

        //construct components
        loginButton = new JButton ("Login");
        signupButton = new JButton ("Sign Up");
        welcomeMessage = new JLabel ("Welcome to Trains Of Sheffield!");
        loginMessage = new JLabel ("If you already have an account, please click Login.");
        signupMessage = new JLabel ("If you do not have an account, please click Sign Up.");

        //adjust size and set layout
        setPreferredSize (new Dimension (775, 431));
        setLayout (null);

        //add components
        this.add(loginButton);
        this.add (signupButton);
        this.add (welcomeMessage);
        this.add (loginMessage);
        this.add (signupMessage);

        //set component bounds (only needed by Absolute Positioning)
        loginButton.setBounds (340, 150, 80, 30);
        signupButton.setBounds (340, 235, 80, 30);
        welcomeMessage.setBounds (290, 5, 230, 45);
        loginMessage.setBounds (240, 115, 400, 25);
        signupMessage.setBounds (240, 200, 400, 25);

        // Create an ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                dispose();

                LoginView loginView = null;
                try {
                    loginView = new LoginView(connection);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                loginView.setVisible(true);
            }
        });

        // Create an ActionListener for the signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                dispose();

                SignupView signupView = null;
                try {
                    signupView = new SignupView(connection);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                signupView.setVisible(true);
            }
        });

        setVisible (true);
    }
}
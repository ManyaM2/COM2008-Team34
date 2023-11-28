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

    public WelcomeView(Connection connection) throws HeadlessException {
        this.setTitle("Trains Of Sheffield");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the background color of the frame to light blue
        getContentPane().setBackground(new Color(232, 244, 248));

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize.width / 2, screenSize.height / 2);
        setLocation(screenSize.width / 4, screenSize.height / 4);

        // Construct components
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");

        JLabel helloText = new JLabel("Hello User" );
        JLabel orText = new JLabel("OR");
        welcomeMessage = new JLabel(" Welcome To Trains Of Sheffield!");
        helloText.setForeground(new Color(0, 0, 139));
        helloText.setFont(new Font("SansSerif", Font.BOLD, 30));
        welcomeMessage.setForeground(new Color(0, 0, 139));
        welcomeMessage.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        loginMessage = new JLabel("If you would like to continue, please click ");
        loginMessage.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        orText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        // Adjust size and set layout
        setPreferredSize(new Dimension(775, 431));
        setLayout(null);

        // Add components
        this.add(loginButton);
        this.add(signupButton);
        this.add(helloText);
        this.add(orText);
        this.add(welcomeMessage);
        this.add(loginMessage);

        helloText.setBounds(290, 70, 500, 45);
        welcomeMessage.setBounds(140, 110, 500, 45);

        loginMessage.setBounds(190, 180, 400, 25);
        loginButton.setBounds(275, 220, 80, 30);
        orText.setBounds(370, 220, 80, 30);

        signupButton.setBounds(415, 220, 80, 30);

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
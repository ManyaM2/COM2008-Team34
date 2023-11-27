import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class ManagerView extends JFrame {
    private JMenuBar menu;
    private JTextArea staffDisplay;
    private DecimalFormat moneyFormat = new DecimalFormat("0.00");
    private JPanel staffPanel;

    private JPanel emailPanel;

    public ManagerView(Connection connection) throws SQLException {
        DbUpdateOperations dbUpdates = new DbUpdateOperations();
        this.setTitle("Trains of Sheffield (Manager) | Staff List");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize((screenSize.width / 2) + 100, screenSize.height / 2);
        setLocation(screenSize.width / 4, screenSize.height / 4);

        // Make menu bar
        JMenuItem backItem = new JMenuItem("<<<");
        backItem.setMaximumSize(new Dimension(45, 50));

        backItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                dispose();
                // Open a new window
                StaffView newWindow = null;
                try {
                    newWindow = new StaffView(connection);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                newWindow.setVisible(true);
            }
        });


        emailPanel = new JPanel(new FlowLayout());

        JLabel hiUser = new JLabel("Hi " + CurrentUserManager.getCurrentUser().getUserForename() + "!  ");

        menu = new JMenuBar();
        menu.add(backItem);
        menu.add(Box.createGlue());
        menu.add(hiUser);

        staffPanel = new JPanel();
        GridLayout layout = new GridLayout(0, 1);
        layout.setHgap(5);
        layout.setVgap(0);
        staffPanel.setLayout(layout);

        // Allow the manager to promote customers
        JTextField emailField = new JTextField();
        emailField.setColumns(20);
        JLabel emailLabel = new JLabel("Enter user email: ");
        JButton findUserButton = new JButton("Promote User");
        findUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();

                // ensure the email is valid
                if (!email.contains("@") || !email.contains(".") || email.isBlank()){
                    JOptionPane.showMessageDialog(staffPanel,  "Invalid email supplied",
                            ("User not found"), 0);
                } else { //Promote user if a valid email is supplied
                    if (dbUpdates.addToStaff(connection, email)){
                        JOptionPane.showMessageDialog(staffPanel,  email + " promoted to staff",
                                ("User Promoted"), 1);
                        loadStaffPanel(connection);
                    } else {
                        JOptionPane.showMessageDialog(staffPanel,
                                "Email does not exist or user is already staff",
                                ("User not promoted"), 0);
                    }
                }
            }
        });
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        emailPanel.add(findUserButton);
        emailPanel.setMaximumSize(new Dimension(emailPanel.getWidth(), 50));
        emailPanel.setMinimumSize(new Dimension(emailPanel.getWidth(), 50));
        staffPanel.add(emailPanel);

        loadStaffPanel(connection);

        //Encapsulate the staff panel in a scrollable panel (to make it scrollable)
        JScrollPane scrollableProducts = new JScrollPane(staffPanel);

        // Add the components to the frame
        this.setLayout(new BorderLayout());
        this.add(menu, BorderLayout.NORTH);
        this.add(scrollableProducts, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public void loadStaffPanel(Connection connection) {
        DatabaseOperations databaseOperations = new DatabaseOperations();
        staffPanel.removeAll();
        staffPanel.add(emailPanel);
        List<User> staff = null;
        try {
            staff = databaseOperations.getUserOfRole(connection, "Staff");
            for (User s : staff) {
                staffPanel.add(getStaffSection(s, connection));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        revalidate();
        repaint();
    }

    public JPanel getStaffSection(User s, Connection connection){
        DbUpdateOperations dbUpdates = new DbUpdateOperations();
        JPanel staffSection = new JPanel();
        JPanel buttonPanel = new JPanel();

        //Make a button to remove the user from staff
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbUpdates.removeFromStaff(connection, s);
                JOptionPane.showMessageDialog(removeButton,  s.getUserEmail() + " Removed",
                        ("Staff removed"), 1);
                loadStaffPanel(connection);
            }
        });

        staffSection.setLayout(new FlowLayout());

        //Display staff metadata
        staffDisplay = new JTextArea(3, 50);
        staffDisplay.setMaximumSize(new Dimension(250, 80));
        staffDisplay.setMinimumSize(new Dimension(200, 40));
        staffDisplay.setText("\n " + s.getUserEmail() + " | " + s.getUserForename() + " " + s.getUserSurname());
        staffDisplay.setEditable(false);

        buttonPanel.add(removeButton);
        staffSection.add(staffDisplay);
        staffSection.add(buttonPanel);
        return staffSection;
    }
}

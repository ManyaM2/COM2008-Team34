import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class ManagerView extends JFrame {
    private JMenuBar menu;
    private JTextArea productDisplay;
    private DecimalFormat moneyFormat = new DecimalFormat("0.00");
    private JPanel staffPanel;

    public ManagerView(Connection connection) throws SQLException {
        DatabaseOperations databaseOperations = new DatabaseOperations();
        this.setTitle("Trains of Sheffield (Manager) | Staff List");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize((screenSize.width / 2) + 100, screenSize.height / 2);
        setLocation(screenSize.width / 4, screenSize.height / 4);

        // Make menu bar
        JMenuItem backItem = new JMenuItem("<<<");
        backItem.setMaximumSize(new Dimension(45, 50));

        menu = new JMenuBar();
        menu.add(backItem);

        // Display the products
        staffPanel = new JPanel();
        GridLayout layout = new GridLayout(0, 1);
        layout.setHgap(5);
        layout.setVgap(0);
        staffPanel.setLayout(layout);

        JTextField emailField = new JTextField();
        emailField.setColumns(20);
        JLabel emailLabel = new JLabel("Enter staff email:");
        JPanel emailPanel = new JPanel(new FlowLayout());
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        staffPanel.add(emailPanel);

        java.util.List<Product> products = databaseOperations.getProducts(connection);
        for (Product p : products) {
            staffPanel.add(getProductSection(p, connection));
        }

        //Encapsulate the staff panel in a scrollable panel (to make it scrollable)
        JScrollPane scrollableProducts = new JScrollPane(staffPanel);

        // Add the components to the frame
        this.setLayout(new BorderLayout());
        this.add(menu, BorderLayout.NORTH);
        this.add(scrollableProducts, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public JPanel getProductSection(Product p, Connection connection){
        JPanel productSection = new JPanel();
        JPanel buttonPanel = new JPanel();

        //Make a select button and add product to order when pressed
        JButton selectButton = new JButton("Order " + p.getProductCode());
        AddOrderActionListener actionListener = new AddOrderActionListener(p, this, connection);
        selectButton.addActionListener(actionListener);

        //Make a view set contents button and display set contents when pressed
        JButton viewContentsButton = new JButton("View Set Contents");
        viewContentsButton.setMaximumSize(new Dimension(100,30));
        viewContentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(viewContentsButton,  ((Set)p).productsAsString(connection),
                        (p.getProductCode() + " Contents"), 1);
            }
        });

        BoxLayout layout = new BoxLayout(productSection, BoxLayout.Y_AXIS);
        productSection.setLayout(layout);

        //Display product metadata
        productDisplay = new JTextArea(10, 20);
        productDisplay.setMaximumSize(new Dimension(250, 80));
        productDisplay.setMinimumSize(new Dimension(200, 40));
        productDisplay.setText("\n " + p.getProductCode() + " | " + p.getProductName() + "\n " + p.getBrandName() +
                "\n " + p.getGauge() + " Gauge (" + p.getScale() + " Scale) \n ");
        char productType = p.getProductCode().charAt(0);

        // Display different data depending on the type of the product
        if (p instanceof Locomotive)
            productDisplay.append("\n " + ((Locomotive)p).getEraCode() + "\n " + ((Locomotive)p).getDccCode());
        if (p instanceof RollingStock)
            productDisplay.append("\n " + ((RollingStock)p).getEraCode());
        if (p instanceof Controller)
            productDisplay.append("\n " + ((Controller)p).getTypeName());
        if (p instanceof Set) {
            if (p.getProductCode().charAt(0) == 'M')
                productDisplay.append("\n " + ((Set)p).getEra() + "\n Controller Type: " +
                        ((Set)p).getControllerType());
            buttonPanel.add(viewContentsButton);
        }

        productDisplay.append("\n £" + p.getRetailPrice());
        if (p.inStock())
            productDisplay.append("\n In stock");
        else
            productDisplay.append("\n Out of stock");
        productDisplay.setEditable(false);

        //Add button to order the product
        buttonPanel.add(selectButton);
        productSection.add(productDisplay);
        productSection.add(buttonPanel);
        return productSection;
    }
}

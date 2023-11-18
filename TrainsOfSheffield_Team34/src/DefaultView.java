import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultView extends JFrame {
    private JMenuBar menu;
    private JButton staffButton;
    private JTextArea productDisplay;

    public DefaultView(Connection connection, User currentUser) throws SQLException {
        DatabaseOperations databaseOperations = new DatabaseOperations();
        this.setTitle("Trains of Sheffield | Home");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize.width / 2, screenSize.height / 2);
        setLocation(screenSize.width / 4, screenSize.height / 4);

        // Make menu bar
        JMenu homeMenu = new JMenu("Home");
        JMenu productsMenu = new JMenu("Products");
        JMenuItem locomotivesItem = new JMenuItem("Locomotives");
        JMenuItem trackPacksItem = new JMenuItem("Track Packs");
        JMenuItem trainSetsItem = new JMenuItem("Train Sets");
        JMenuItem controllersItem = new JMenuItem("Controllers");
        JMenuItem trackPiecesItem = new JMenuItem("Track Pieces");
        JMenuItem rollingStockItem = new JMenuItem("Rolling Stock");

        productsMenu.add(locomotivesItem);
        productsMenu.add(trackPacksItem);
        productsMenu.add(trainSetsItem);
        productsMenu.add(controllersItem);
        productsMenu.add(trackPiecesItem);
        productsMenu.add(rollingStockItem);

        JMenu profileMenu = new JMenu("Profile");
        JMenuItem editDetailsItem = new JMenuItem("Edit Details");
        JMenuItem myOrdersItem = new JMenuItem("My Orders");

        profileMenu.add(editDetailsItem);
        profileMenu.add(myOrdersItem);

        menu = new JMenuBar();
        menu.add(homeMenu);
        menu.add(productsMenu);
        menu.add(profileMenu);

        //Make the staff area button
        JPanel bottomPanel = new JPanel();
        staffButton = new JButton("Staff Area");
        bottomPanel.add(staffButton);

        // Display the products
        JPanel productPanel = new JPanel();
        GridLayout layout = new GridLayout(0,3);
        layout.setHgap(10);
        layout.setVgap(5);
        productPanel.setLayout(layout);
        List<Product> products = databaseOperations.getProduct(connection);
        for (Product p : products){
            productPanel.add(getProductSection(p));
        }
        //Encapsulate the product panel in a scrollable panel (to make it scrollable)
        JScrollPane scrollableProducts = new JScrollPane(productPanel);

        // Add the components to the frame
        this.setLayout(new BorderLayout());
        this.add(menu, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(scrollableProducts, BorderLayout.CENTER);
        staffButton.setMaximumSize(new Dimension(40, staffButton.getPreferredSize().height));

        //Don't display the staff button if the user is not a staff/manager
        if (!currentUser.getUserRoles().contains("staff") && !currentUser.getUserRoles().contains("manager"))
            staffButton.setVisible(false);

        this.setVisible(true);


        // Create an ActionListener for the staff button
        staffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Go to the staff view
            }
        });
        locomotivesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Locomotives");
                productPanel.removeAll();
                //Display locos
                revalidate();
                repaint();
            }
        });

        trackPacksItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Track Packs");
                productPanel.removeAll();
                //Display track packs
                revalidate();
                repaint();
            }
        });

        trainSetsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Train Sets");
                productPanel.removeAll();
                //Display train sets
                revalidate();
                repaint();
            }
        });

        controllersItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Controllers");
                productPanel.removeAll();
                //Display controllers
                revalidate();
                repaint();
            }
        });

        trackPiecesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Track Pieces");
                productPanel.removeAll();
                //Display track pieces
                revalidate();
                repaint();
            }
        });

        rollingStockItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Rolling Stock");
                productPanel.removeAll();
                //Display rolling stock
                revalidate();
                repaint();
            }
        });

        editDetailsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Profile - Edit Details");
                productPanel.removeAll();
                //Edit details
                revalidate();
                repaint();
            }
        });

        myOrdersItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Profile - My Orders");
                productPanel.removeAll();
                //Display + edit orders
                revalidate();
                repaint();
            }
        });

        homeMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Don't know how to go back
            }
        });
    }

    /**
     * Add the metadata of each product onto the part of the screen they will be displayed
     * @param p the product being displayed
     * @return The panel displaying the product
     */
    public JPanel getProductSection(Product p){
        JPanel productSection = new JPanel();
        JPanel buttonPanel = new JPanel();
        JButton selectButton = new JButton("Order " + p.getProductCode());
        JButton viewContentsButton = new JButton("View " + p.getProductCode() + " Contents");

        productSection.setLayout(new GridLayout(2,1));

        //Display product metadata
        productDisplay = new JTextArea(7, 10);
        productDisplay.setMaximumSize(new Dimension(200, 130));
        productDisplay.setText("\n " + p.getProductCode() + " | " + p.getProductName() + "\n " + p.getBrandName() +
                "\n " + p.getGauge() + " Gauge (" + p.getScale() + " Scale) \n ");
        char productType = p.getProductCode().charAt(0);
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
            else
                productDisplay.append("\n " + ((Set)p).getEra());
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


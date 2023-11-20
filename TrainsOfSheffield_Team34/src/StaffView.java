import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaffView extends JFrame {
    private JMenuBar menu;
    private JButton managerButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextArea productDisplay;
    private JTextArea orderDisplay;

    public StaffView(Connection connection, User currentUser) throws SQLException {
        DatabaseOperations databaseOperations = new DatabaseOperations();
        this.setTitle("Trains of Sheffield | Home");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize((screenSize.width / 2)+50, screenSize.height / 2);
        setLocation(screenSize.width / 4, screenSize.height / 4);

        // Make menu bar
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

        JMenuItem homeItem = new JMenuItem("Home");
        homeItem.setMaximumSize(new Dimension(45, 50));

        JMenu profileMenu = new JMenu("Profile");
        JMenuItem editDetailsItem = new JMenuItem("Edit Details");
        JMenuItem myOrdersItem = new JMenuItem("My Orders");

        profileMenu.add(editDetailsItem);
        profileMenu.add(myOrdersItem);

        JMenuItem detailsItem = new JMenuItem("View Orders");
        detailsItem.setMaximumSize((new Dimension(80,50)));

        menu = new JMenuBar();
        menu.add(homeItem);
        menu.add(productsMenu);
        menu.add(profileMenu);
        menu.add(detailsItem);

        // Display the products
        JPanel productPanel = new JPanel();
        GridLayout layout = new GridLayout(0,3);
        layout.setHgap(10);
        layout.setVgap(0);
        productPanel.setLayout(layout);
        List<Product> products = databaseOperations.getProducts(connection);
        for (Product p : products){
            productPanel.add(getProductSection(p));
        }
        //Encapsulate the product panel in a scrollable panel (to make it scrollable)
        JScrollPane scrollableProducts = new JScrollPane(productPanel);

        //Make the manager area button
        JPanel belowPanel = new JPanel();
        managerButton = new JButton("Manager Area");
        belowPanel.add(managerButton);

        // Add the components to the frame
        this.setLayout(new BorderLayout());
        this.add(menu, BorderLayout.NORTH);
        this.add(belowPanel, BorderLayout.SOUTH);
        this.add(scrollableProducts, BorderLayout.CENTER);

        if (!currentUser.getUserRoles().contains("manager"))
            managerButton.setVisible(false);
        this.setVisible(true);

        locomotivesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Locomotives");
                productPanel.removeAll();
                List<Locomotive> products = null;
                try {
                    products = databaseOperations.getLocomotives(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                revalidate();
                repaint();
            }
        });

        trackPacksItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Track Packs");
                productPanel.removeAll();
                List<Set> products = null;
                try {
                    products = databaseOperations.getTrackPacks(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                revalidate();
                repaint();
            }
        });

        trainSetsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Train Sets");
                productPanel.removeAll();
                List<Set> products = null;
                try {
                    products = databaseOperations.getTrainSets(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                revalidate();
                repaint();
            }
        });

        controllersItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Controllers");
                productPanel.removeAll();
                List<Controller> products = null;
                try {
                    products = databaseOperations.getControllers(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                revalidate();
                repaint();
            }
        });

        trackPiecesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Track Pieces");
                productPanel.removeAll();
                List<Product> products = null;
                try {
                    products = databaseOperations.getTrackPieces(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                revalidate();
                repaint();
            }
        });

        rollingStockItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Rolling Stock");
                productPanel.removeAll();
                List<RollingStock> products = null;
                try {
                    products = databaseOperations.getRollingStock(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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

        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home");
                productPanel.removeAll();
                List<Product> products = null;
                try {
                    products = databaseOperations.getProducts(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                revalidate();
                repaint();
            }
        });

        detailsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Orders");
                productPanel.removeAll();
                GridLayout layout1 = new GridLayout(1,1);
                layout1.setHgap(10);
                layout1.setVgap(0);
                productPanel.setLayout(layout1);
                List<Order> orders = null;
                try {
                    orders = databaseOperations.getOrders(connection);
                    for (Order o : orders){
                        productPanel.add(getOrderSection(o));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                //Encapsulate the product panel in a scrollable panel (to make it scrollable)
                JScrollPane scrollableOrders = new JScrollPane(productPanel);

                // Add the components to the frame
                setLayout(new BorderLayout());
                add(menu, BorderLayout.NORTH);
                add(belowPanel, BorderLayout.SOUTH);
                add(scrollableOrders, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        /*editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Product> products = null;
                try {
                    products = databaseOperations.updateProductDetails(connection, product);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                revalidate();
                repaint();
            }
        });*/
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
        editButton = new JButton ("Edit");
        deleteButton = new JButton ("Delete");
        JButton viewContentsButton = new JButton("View " + p.getProductCode() + " Contents");

        GridLayout layout = new GridLayout(2,1);
        BoxLayout layout2 = new BoxLayout(productSection, BoxLayout.Y_AXIS);
        productSection.setLayout(layout2);


        //Display product metadata
        productDisplay = new JTextArea(10, 20);
        productDisplay.setMaximumSize(new Dimension(250, 80));
        productDisplay.setMinimumSize(new Dimension(200, 80));
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

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        //Add button to order the product
        buttonPanel.add(selectButton);
        productSection.add(productDisplay);
        productSection.add(buttonPanel);
        return productSection;
    }

    public JPanel getOrderSection(Order o){
        JPanel orderSection = new JPanel();
        JPanel buttonPanel = new JPanel();
        JButton selectButton = new JButton("Order " + o.getOrderNumber());
        JButton fulfillButton = new JButton("Fulfill");
        deleteButton = new JButton ("Delete");

        GridLayout layout1 = new GridLayout(2,1);
        BoxLayout layout2 = new BoxLayout(orderSection, BoxLayout.Y_AXIS);
        orderSection.setLayout(layout2);


        //Display product metadata
        orderDisplay = new JTextArea(50, 15);
        orderDisplay.setMaximumSize(new Dimension(250, 80));
        orderDisplay.setMinimumSize(new Dimension(200, 80));
        orderDisplay.setText("\n " + o.getOrderNumber() + " | " + o.getOrderLines() + " | " + o.getUserID() + "\n " + o.getStatus() +
                "\n " + o.date() + "\n"  );

        buttonPanel.add(fulfillButton);
        buttonPanel.add(deleteButton);

        //Add button to order the product
        buttonPanel.add(selectButton);
        orderSection.add(orderDisplay);
        orderSection.add(buttonPanel);
        return orderSection;
    }
}


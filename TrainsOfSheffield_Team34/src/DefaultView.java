import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class DefaultView extends JFrame {
    private JMenuBar menu;
    private JButton staffButton;
    private JTextArea productDisplay;
    private DecimalFormat moneyFormat = new DecimalFormat("0.00");
    private JPanel productPanel;

    public DefaultView(Connection connection) throws SQLException {
        DatabaseOperations databaseOperations = new DatabaseOperations();
        this.setTitle("Trains of Sheffield | Home");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize((screenSize.width / 2) + 100, screenSize.height / 2);
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

        menu = new JMenuBar();
        menu.add(homeItem);
        menu.add(productsMenu);
        menu.add(profileMenu);

        //Make the staff area button
        JPanel bottomPanel = new JPanel();
        staffButton = new JButton("Staff Area");
        bottomPanel.add(staffButton);

        // Display the products
        productPanel = new JPanel();
        GridLayout layout = new GridLayout(0,3);
        layout.setHgap(5);
        layout.setVgap(0);
        productPanel.setLayout(layout);
        List<Product> products = databaseOperations.getProducts(connection);
        for (Product p : products){
            productPanel.add(getProductSection(p, connection));
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
        if (!CurrentUserManager.getCurrentUser().getUserRoles().contains("staff") && !CurrentUserManager.getCurrentUser().getUserRoles().contains("manager"))
            staffButton.setVisible(false);

        this.setVisible(true);


        // Create an ActionListener for the staff button
        staffButton.addActionListener(new ActionListener() {
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
        locomotivesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home - Locomotives");
                productPanel.removeAll();
                List<Locomotive> products = null;
                try {
                    products = databaseOperations.getLocomotives(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p, connection));
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
                GridLayout layout = new GridLayout(0,3);
                layout.setHgap(5);
                layout.setVgap(0);
                productPanel.setLayout(layout);
                List<Set> products = null;
                try {
                    products = databaseOperations.getTrackPacks(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p, connection));
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
                GridLayout layout = new GridLayout(0,3);
                layout.setHgap(5);
                layout.setVgap(0);
                productPanel.setLayout(layout);
                List<Set> products = null;
                try {
                    products = databaseOperations.getTrainSets(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p, connection));
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
                GridLayout layout = new GridLayout(0,3);
                layout.setHgap(5);
                layout.setVgap(0);
                productPanel.setLayout(layout);
                List<Controller> products = null;
                try {
                    products = databaseOperations.getControllers(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p, connection));
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
                GridLayout layout = new GridLayout(0,3);
                layout.setHgap(5);
                layout.setVgap(0);
                productPanel.setLayout(layout);
                List<Product> products = null;
                try {
                    products = databaseOperations.getTrackPieces(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p, connection));
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
                GridLayout layout = new GridLayout(0,3);
                layout.setHgap(5);
                layout.setVgap(0);
                productPanel.setLayout(layout);
                List<RollingStock> products = null;
                try {
                    products = databaseOperations.getRollingStock(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p, connection));
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
                GridLayout layout = new GridLayout(0,3);
                layout.setHgap(5);
                layout.setVgap(0);
                productPanel.setLayout(layout);
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
                BoxLayout layout = new BoxLayout(productPanel, BoxLayout.Y_AXIS);
                productPanel.setLayout(layout);
                List<Order> orders = null;
                try {
                    orders = databaseOperations.getOrders(connection, CurrentUserManager.getCurrentUser());
                    for (Order o : orders){
                        productPanel.add(getOrderPanel(o, connection));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                revalidate();
                repaint();
            }
        });

        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield | Home");
                productPanel.removeAll();
                GridLayout layout = new GridLayout(0,3);
                layout.setHgap(5);
                layout.setVgap(0);
                productPanel.setLayout(layout);
                List<Product> products = null;
                try {
                    products = databaseOperations.getProducts(connection);
                    for (Product p : products){
                        productPanel.add(getProductSection(p, connection));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                revalidate();
                repaint();
            }
        });
    }

    /**
     * Add the metadata of each product onto the part of the screen they will be displayed
     * @param p the product being displayed
     * @return The panel displaying the product
     */
    public JPanel getProductSection(Product p, Connection connection){
        JPanel productSection = new JPanel();
        JPanel buttonPanel = new JPanel();

        //Make a select button and add product to order when pressed
        JButton selectButton = new JButton("Order " + p.getProductCode());
        AddOrderActionListener actionListener = new AddOrderActionListener(p, this, connection);
        selectButton.addActionListener(actionListener);

        //Make a view set contents button and display set contents when pressed
        JButton viewContentsButton = new JButton("View " + p.getProductCode() + " Contents");
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
        productDisplay.setMinimumSize(new Dimension(200, 80));
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

    public JPanel getOrderPanel(Order o, Connection connection){
        DatabaseOperations dbOps = new DatabaseOperations();

        // Set the layout and border of the order section
        JPanel orderPanel = new JPanel();
        GridLayout layout = new GridLayout(0,3);
        layout.setHgap(5);
        layout.setVgap(0);
        orderPanel.setLayout(layout);
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
                ("Order " + o.getOrderNumber() + " | Status: " + o.getStatus()) + " | " + o.getDateMade());
        title.setTitleJustification(TitledBorder.LEFT);
        orderPanel.setBorder(title);

        // Add the products to the order section
        for (OrderLine ol : o.getOrderLines()){
            orderPanel.add(getOrderLine(ol, connection));
        }

        // Add a button to confirm the order
        if (o.getStatus() == OrderStatus.PENDING){
            JPanel buttonPanel = new JPanel();
            JButton confirmButton = new JButton("Confirm order");
            confirmButton.setMaximumSize(new Dimension(100,30));
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(confirmButton, "Order confirmed");
                    //TODO - Confirm an order, if the order is confirmed for the first time, request banking details
                    // Put a haveConfirmed field in the Users table? -- Remember Validation!!
                }
            });

            buttonPanel.add(confirmButton);
            orderPanel.add(buttonPanel);
        }

        return orderPanel;
    }

    public JPanel getOrderLine(OrderLine ol, Connection connection){
        DatabaseOperations dbOps = new DatabaseOperations();
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        JPanel orderLineSection = new JPanel();
        JPanel buttonPanel = new JPanel();
        String pCode = ol.getProductCode();

        //Get the product represented in the orderLine
        Product lineProduct = null;
        try {
            List<Product> products = dbOps.getProducts(connection);
            for (Product p : products){
                if (p.getProductCode().equals(pCode) && p != null)
                    lineProduct = p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Make a button to remove the orderline from the order
        JButton removeOrderLineButton = new JButton("Remove");
        removeOrderLineButton.setMaximumSize(new Dimension(100,30));
        removeOrderLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dbUpdateOps.removeOrderLine(connection, ol);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(removeOrderLineButton, "Orderline removed");
            }
        });

        //Make a spinner to edit the quantity of the product
        JSpinner selectQuantity = new JSpinner(new SpinnerNumberModel(ol.getProductQuantity(), 1,
                lineProduct.getStockLevel(), 1));
        JLabel qLabel = new JLabel("Edit quantity: ");
        JSpinner finalSelectQuantity = selectQuantity;
        selectQuantity.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ol.setProductQuantity((int)finalSelectQuantity.getValue());
                try {
                    dbUpdateOps.updateOrderLineQuantity(connection, ol);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        BoxLayout layout = new BoxLayout(orderLineSection, BoxLayout.Y_AXIS);
        orderLineSection.setLayout(layout);

        //Display orderline metadata
        JTextArea orderlineDisplay = new JTextArea(10, 20);
        orderlineDisplay.setMaximumSize(new Dimension(250, 80));
        orderlineDisplay.setMinimumSize(new Dimension(200, 80));
        if (lineProduct != null){
            float lineCost = ol.lineCost(ol.getProductQuantity(), (float)lineProduct.getRetailPrice());
            orderlineDisplay.setText(String.format("\n " + ol.getProductCode() + " | " + lineProduct.getProductName() +
                    "\n " + lineProduct.getBrandName() + "\n Product Quantity: " + ol.getProductQuantity() +
                    "\n Line cost: £" + moneyFormat.format(lineCost)));
        }
        orderlineDisplay.setEditable(false);

        buttonPanel.add(qLabel);
        buttonPanel.add(finalSelectQuantity);
        buttonPanel.add(removeOrderLineButton);
        orderLineSection.add(orderlineDisplay);
        orderLineSection.add(buttonPanel);
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
                ("Orderline " + ol.getLineNumber()));
        title.setTitleJustification(TitledBorder.LEFT);
        orderLineSection.setBorder(title);
        return orderLineSection;
    }
}


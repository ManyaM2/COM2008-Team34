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
import java.time.Year;
import java.util.Collections;
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
        if (!CurrentUserManager.getCurrentUser().getUserRoles().contains("Staff") &&
                !CurrentUserManager.getCurrentUser().getUserRoles().contains("Manager"))
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
                GridLayout layout = new GridLayout(0,3);
                layout.setHgap(5);
                layout.setVgap(0);
                productPanel.setLayout(layout);
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
                productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
                productPanel.add(editProfile(connection));
                revalidate();
                repaint();
            }
        });

        myOrdersItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadMyOrders(connection, databaseOperations);
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
        productDisplay.setMinimumSize(new Dimension(200, 80));
        productDisplay.setText("\n " + p.getProductCode() + " | " + p.getProductName() + "\n " + p.getBrandName() +
                "\n " + p.getGauge() + " Gauge (" + p.getScale() + " Scale) \n ");

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
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        User currentUser = CurrentUserManager.getCurrentUser();

        JPanel orderPanel = new JPanel();
        JButton orderButton = new JButton("Order " + o.getOrderNumber() + " | Date: " + o.getDateMade());
        JPanel orderButtonPanel = new JPanel();
        orderButtonPanel.add(orderButton);
        orderPanel.add(orderButtonPanel);

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display order metadata
                JPanel newOrderPanel = new JPanel();
                GridLayout layout = new GridLayout(0,3);
                layout.setHgap(5);
                layout.setVgap(0);
                newOrderPanel.setLayout(layout);
                JPanel metadataPanel = new JPanel();
                metadataPanel.setLayout(new BoxLayout(metadataPanel, BoxLayout.Y_AXIS));
                JTextArea orderMetadataDisplay = new JTextArea(10, 20);
                orderMetadataDisplay.setMaximumSize(new Dimension(250, 80));
                orderMetadataDisplay.setMinimumSize(new Dimension(200, 80));
                orderMetadataDisplay.setText(" Status: " + o.getStatus() + " | Date: " + o.getDateMade() + "\n " +
                        currentUser.getUserForename() + " " + currentUser.getUserSurname() + "\n " +
                        currentUser.getUserEmail() + "\n\n " + currentUser.getAddress(connection).toString() +
                        "\n\n Total cost: £" + moneyFormat.format(o.totalCost(connection)));
                orderMetadataDisplay.setEditable(false);
                metadataPanel.add(orderMetadataDisplay);

                JPanel buttonPanel = new JPanel();
                // Add a button to confirm the order
                if (o.getStatus() == OrderStatus.PENDING){
                    JButton confirmButton = new JButton("Confirm order");
                    confirmButton.setMaximumSize(new Dimension(100,30));
                    confirmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            try {
                                if (!dbOps.checkIfConfirmed(connection))
                                    addBankDetails(connection, o, true);
                                else {
                                    o.setStatus(connection, OrderStatus.CONFIRMED);
                                    JOptionPane.showMessageDialog(confirmButton, "Order confirmed");
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            reloadMyOrders(connection, dbOps);
                        }
                    });
                    buttonPanel.add(confirmButton);
                }
                metadataPanel.add(buttonPanel);
                metadataPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
                newOrderPanel.add(metadataPanel);

                // Add the products to the order section
                for (int i = 0; i < o.getOrderLines().size(); i++)
                    newOrderPanel.add(getOrderLine(connection, o.getOrderLines().get(i), o, i+1));

                JOptionPane.showMessageDialog(null, newOrderPanel, "Select Quantity",
                        1);
            }
        });
        return orderPanel;
    }

    public JPanel getOrderLine(Connection connection, OrderLine ol, Order o, int counter){
        boolean outOfStock = false;
        DatabaseOperations dbOps = new DatabaseOperations();
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        JPanel orderLineSection = new JPanel();
        JPanel buttonPanel = new JPanel();

        //Get the product represented in the orderLine
        Product lineProduct = ol.getProduct(connection);

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
                reloadMyOrders(connection, dbOps);
            }
        });

        //Make a spinner to edit the quantity of the product
        JSpinner selectQuantity = null;
        if (lineProduct.getStockLevel() >= 1) {
            selectQuantity = new JSpinner(new SpinnerNumberModel(ol.getProductQuantity(), 1,
                    lineProduct.getStockLevel(), 1));
        } else {
            outOfStock = true;
            selectQuantity = new JSpinner(new SpinnerNumberModel(ol.getProductQuantity(), 1,
                    2, 1));
        }

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
                reloadMyOrders(connection, dbOps);
            }
        });

        BoxLayout layout = new BoxLayout(orderLineSection, BoxLayout.Y_AXIS);
        orderLineSection.setLayout(layout);

        //Display orderline metadata
        JTextArea orderlineDisplay = new JTextArea(10, 20);
        orderlineDisplay.setMaximumSize(new Dimension(250, 80));
        orderlineDisplay.setMinimumSize(new Dimension(200, 80));
        if (lineProduct != null){
            orderlineDisplay.setText(String.format("\n " + ol.getProductCode() + " | " + lineProduct.getProductName() +
                    "\n " + lineProduct.getBrandName() + "\n Product Quantity: " + ol.getProductQuantity() +
                    "\n Line cost: £" + moneyFormat.format(ol.lineCost(connection))));
        }
        orderlineDisplay.setEditable(false);

        if (o.getStatus() == OrderStatus.PENDING && !outOfStock){
            buttonPanel.add(qLabel);
            buttonPanel.add(finalSelectQuantity);
            buttonPanel.add(removeOrderLineButton);
        }
        orderLineSection.add(orderlineDisplay);
        orderLineSection.add(buttonPanel);
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
                ("Orderline " + counter));
        title.setTitleJustification(TitledBorder.LEFT);
        orderLineSection.setBorder(title);
        return orderLineSection;
    }

    public void reloadMyOrders(Connection connection, DatabaseOperations databaseOperations){
        setTitle("Trains of Sheffield | Profile - My Orders");
        productPanel.removeAll();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        List<Order> orders = null;
        try {
            // Display pending orders
            orders = databaseOperations.getOrders(connection, CurrentUserManager.getCurrentUser());
            orders.removeIf(n -> (n.getStatus() != OrderStatus.PENDING));
            for (Order o : orders){
                // Only display an order if it has 1 or more orderlines
                if (!o.getOrderLines().isEmpty())
                    productPanel.add(getOrderPanel(o, connection));
            }

            // Display confirmed orders
            orders = databaseOperations.getOrders(connection, CurrentUserManager.getCurrentUser());
            orders.removeIf(n -> (n.getStatus() != OrderStatus.CONFIRMED));
            Collections.reverse(orders); //Newest order displayed first
            for (Order o : orders){
                productPanel.add(getOrderPanel(o, connection));
            }

            // Display fulfilled orders
            orders = databaseOperations.getOrders(connection, CurrentUserManager.getCurrentUser());
            orders.removeIf(n -> (n.getStatus() != OrderStatus.FULFILLED));
            Collections.reverse(orders);
            for (Order o : orders){
                productPanel.add(getOrderPanel(o, connection));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        revalidate();
        repaint();
    }

    private void addBankDetails(Connection connection, Order o, Boolean confirmingOrder) {
        User currentUser = CurrentUserManager.getCurrentUser();

        // Set up comboboxes for the expiry date
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        JComboBox<String> expiryMonth = new JComboBox<>(months);
        int currentYear = Year.now().getValue();
        Integer[] expiryYears = new Integer[8];
        for(int i = currentYear; i < currentYear + 8;i++)
            expiryYears[i - (currentYear)] = i % 1000;
        JComboBox<Integer> expiryYear = new JComboBox<>(expiryYears);

        //Add fields and labels to the form
        JTextField cardName = new JTextField("");
        JTextField cardHolderName = new JTextField("");
        JTextField cardNumber = new JTextField("");
        JTextField securityCode = new JTextField("");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Bank card name "));
        panel.add(cardName);
        panel.add(new JLabel("Bank card holder name "));
        panel.add(cardHolderName);
        panel.add(new JLabel("Bank card number "));
        panel.add(cardNumber);
        panel.add(new JLabel("Security code "));
        panel.add(securityCode);
        panel.add(new JLabel("Expiry date (mm/yy) "));
        panel.add(expiryMonth);
        panel.add(expiryYear);

        // Process results
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Banking Details",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String cName = cardName.getText();
                String cHolderName = cardHolderName.getText();
                String cNum = cardNumber.getText().replaceAll("\\s", "");
                String secCode = securityCode.getText();
                String expiryDate = expiryMonth.getSelectedItem() + "/" + expiryYear.getSelectedItem();

                // Ensure the details entered are of the correct length
                if (cNum.length() != 16 || secCode.length() != 3 || cName.isEmpty() || cHolderName.isEmpty()){
                    JOptionPane.showMessageDialog(panel,"Invalid details provided ",
                            "Invalid Entry", 0);
                }
                else{
                    // Exception caught if parse fails
                    Integer.parseInt(cNum.substring(0,7));
                    Integer.parseInt(cNum.substring(8));
                    Integer.parseInt(secCode);
                    BankDetails bd = new BankDetails(cName, cNum, expiryDate, cHolderName, secCode);
                    bd.addToDatabase(connection);
                    if (confirmingOrder){
                        o.setStatus(connection, OrderStatus.CONFIRMED);
                        currentUser.setConfirmed(connection);
                    }
                    if (confirmingOrder)
                        JOptionPane.showMessageDialog(panel, "Order confirmed");
                    else
                        JOptionPane.showMessageDialog(panel, "Details updated");
                }
            }
            catch (NumberFormatException e) {  // Ensure card number and security code are integer entries
                JOptionPane.showMessageDialog(panel, "Invalid details provided",
                        "Invalid Entry", 0);
            }
        } else { }
    }

    private void editAddress(Connection connection) {
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
                JOptionPane.showMessageDialog(panel,"Update failed: Please fill in all the fields ",
                        "Invalid Entry", 0);
            } else {
                Address newAddress = new Address(houseNumberField.getText(), roadNameField.getText(),
                        cityNameField.getText(), postcodeField.getText());
                // Update address in database
                CurrentUserManager.getCurrentUser().updateAddress(connection, newAddress);
                JOptionPane.showMessageDialog(panel, "Address updated");
            }
        }
    }

    private JPanel editProfile(Connection connection) {
        User currentUser = CurrentUserManager.getCurrentUser();

        // Instantiate components
        JTextField forenameField = new JTextField(currentUser.getUserForename());
        JTextField surnameField = new JTextField(currentUser.getUserSurname());
        JTextField emailField = new JTextField(currentUser.getUserEmail());
        forenameField.setColumns(10);
        surnameField.setColumns(10);
        emailField.setColumns(20);
        JButton editAddressButton = new JButton("Edit address");
        JButton editBankDetailsButton = new JButton("Edit bank details");
        JButton applyButton = new JButton("Apply changes");
        JPanel textFields = new JPanel(new FlowLayout());
        textFields.add(new JLabel("Forename: "));
        textFields.add(forenameField);
        textFields.add(new JLabel("Surname: "));
        textFields.add(surnameField);
        textFields.add(new JLabel("Email: "));
        textFields.add(emailField);

        // Add components to the panels
        JPanel editButtons = new JPanel(new FlowLayout());
        JPanel applyButtonPanel = new JPanel();
        JPanel panel = new JPanel();
        editButtons.add(editAddressButton);
        editButtons.add(editBankDetailsButton);
        applyButtonPanel.add(applyButton);
        panel.add(textFields);
        panel.add(editButtons);
        panel.add(applyButtonPanel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add action listeners
        editBankDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBankDetails(connection, null, false);
            }
        });
        editAddressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAddress(connection);
            }
        });
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String forename = forenameField.getText();
                String surname = surnameField.getText();
                String email = emailField.getText();
                // Ensure there is text in all the fields
                if (forename.isBlank() || surname.isBlank() || email.isBlank()) {
                    JOptionPane.showMessageDialog(panel, "Update failed: Please fill in all the fields ",
                            "Invalid Entry", 0);
                } else {
                    // Ensure the email contains the correct characters
                    if (!email.contains("@") || !email.contains(".")){
                        JOptionPane.showMessageDialog(panel, "Update failed: Invalid email address",
                                "Invalid Entry", 0);
                    } else { // Apply changes
                        currentUser.setUserForename(forename);
                        currentUser.setUserSurname(surname);
                        currentUser.setUserEmail(email);

                        // Update user details in the database
                        currentUser.updatePersonalDetails(connection);
                        currentUser.updateEmail(connection);

                        // Update user details in the session
                        CurrentUserManager.setCurrentUser(currentUser);

                        JOptionPane.showMessageDialog(panel, "Details updated");
                    }
                }
            }
        });

        return panel;
    }
}


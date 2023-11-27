import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StaffView extends JFrame {
    private DecimalFormat moneyFormat = new DecimalFormat("0.00");
    private JMenuBar menu;
    private JButton managerButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton addButton;
    private JPanel productPanel;
    private JButton setButton;
    private JPanel orderPanel;
    private JTextArea productDisplay;
    private JTextArea orderDisplay;

    public StaffView(Connection connection) throws SQLException {
        DatabaseOperations databaseOperations = new DatabaseOperations();
        DbUpdateOperations databaseUpdateOps = new DbUpdateOperations();
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

        JMenuItem prevItem = new JMenuItem("<<<");
        prevItem.setMaximumSize(new Dimension(35, 50));

        JMenuItem homeItem = new JMenuItem("Home");
        homeItem.setMaximumSize(new Dimension(45, 50));

        JMenu ordersMenu = new JMenu("Orders");
        JMenuItem pendingOrdersItem = new JMenuItem("Unfulfilled Orders");
        JMenuItem allOrdersItem = new JMenuItem("All Orders");

        ordersMenu.add(pendingOrdersItem);
        ordersMenu.add(allOrdersItem);

        JLabel hiUser = new JLabel("Hi " + CurrentUserManager.getCurrentUser().getUserForename() + "!  ");

        menu = new JMenuBar();
        menu.add(prevItem);
        menu.add(homeItem);
        menu.add(productsMenu);
        menu.add(ordersMenu);


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

        //Make the manager area button
        JPanel belowPanel = new JPanel();
        managerButton = new JButton("Manager Area");
        belowPanel.add(managerButton);

        JPanel newPanel = new JPanel();
        addButton = new JButton("+ Add");
        newPanel.add(addButton);

        // Add the components to the frame
        this.setLayout(new BorderLayout());
        this.add(menu, BorderLayout.NORTH);
        this.add(newPanel, BorderLayout.EAST);
        this.add(belowPanel, BorderLayout.SOUTH);
        this.add(scrollableProducts, BorderLayout.CENTER);

        if (!CurrentUserManager.getCurrentUser().getUserRoles().contains("Manager"))
            managerButton.setVisible(false);
        this.setVisible(true);

        managerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                dispose();
                // Open a new window
                ManagerView newWindow = null;
                try {
                    newWindow = new ManagerView(connection);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                newWindow.setVisible(true);
            }
        });

        prevItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                dispose();
                // Open a new window
                DefaultView newWindow = null;
                try {
                    newWindow = new DefaultView(connection);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                newWindow.setVisible(true);
            }
        });

        locomotivesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield (Staff) | Home - Locomotives");
                addButton.setVisible(true);
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
                setTitle("Trains of Sheffield (Staff) | Home - Track Packs");
                addButton.setVisible(true);
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
                setTitle("Trains of Sheffield (Staff) | Home - Train Sets");
                addButton.setVisible(true);
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
                setTitle("Trains of Sheffield (Staff) | Home - Controllers");
                addButton.setVisible(true);
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
                setTitle("Trains of Sheffield (Staff) | Home - Track Pieces");
                addButton.setVisible(true);
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
                setTitle("Trains of Sheffield (Staff) | Home - Rolling Stock");
                addButton.setVisible(true);
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


        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield (Staff) | Home");
                addButton.setVisible(true);
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

        pendingOrdersItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadMyOrders(connection, databaseOperations, false);
            }
        });

        allOrdersItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadMyOrders(connection, databaseOperations, true);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] productType = {"Track Pack", "Locomotive", "Train Set", "Controller", "Track Piece", "Rolling Stock"};
                JComboBox<String> types = new JComboBox<>(productType);

                JTextField productCode = new JTextField("");
                JTextField brandName = new JTextField("");
                JTextField productName = new JTextField("");
                JTextField retailPrice = new JTextField("");
                JTextField gauge = new JTextField("");
                JTextField stockLevel = new JTextField("");


                JTextField eraCode = new JTextField("");
                JLabel ec = new JLabel("Era Code:");

                JTextField dccCode = new JTextField("");
                JLabel dc = new JLabel("Dcc Code:");

                JTextField typeName = new JTextField("");
                JLabel tn = new JLabel("Type Name:") ;

                JTextField controllerType = new JTextField("");
                JLabel ct = new JLabel("Controller Type:");

                JPanel panel = new JPanel(new GridLayout(0, 1));
                JScrollPane scrollablePanel = new JScrollPane(panel);

                panel.add(new JLabel("Select type:"));
                panel.add(types);

                panel.add(new JLabel("Product Code:"));
                panel.add(productCode);
                panel.add(new JLabel("Brand Name:"));
                panel.add(brandName);
                panel.add(new JLabel("Product Name:"));
                panel.add(productName);
                panel.add(new JLabel("Retail Price:"));
                panel.add(retailPrice);
                panel.add(new JLabel("Gauge:"));
                panel.add(gauge);
                panel.add(new JLabel("Stock Level:"));
                panel.add(stockLevel);

                types.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String pType = types.getSelectedItem().toString();
                        panel.remove(ec);
                        panel.remove(eraCode);
                        panel.remove(dc);
                        panel.remove(dccCode);
                        panel.remove(tn);
                        panel.remove(typeName);
                        panel.remove(ct);
                        panel.remove(controllerType);
                        switch(pType){
                            case "Locomotive":
                                panel.add(ec);
                                panel.add(eraCode);
                                panel.add(dc);
                                panel.add(dccCode);
                                break;
                            case "Controller":
                                panel.add(tn);
                                panel.add(typeName);
                                break;
                            case "Rolling Stock":
                                panel.add(ec);
                                panel.add(eraCode);
                                break;
                            case "Train Set":
                                panel.add(ec);
                                panel.add(eraCode);
                                panel.add(ct);
                                panel.add(controllerType);
                                break;
                        }
                    }
                });

                int result = JOptionPane.showConfirmDialog(null, scrollablePanel, "New Product Details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {

                        String pCode = productCode.getText();
                        String bName = brandName.getText();
                        String pName = productName.getText();
                        Double rPrice = Double.valueOf(retailPrice.getText());
                        String g = gauge.getText();
                        Integer sLevel = Integer.parseInt(stockLevel.getText());
                        Product newProduct = new Product(pCode, bName, pName, rPrice, g, sLevel);

                        //Considerations for the specific types of products
                        String eCode = eraCode.getText();
                        String dCode = dccCode.getText();
                        String tName = typeName.getText();
                        String cType =controllerType.getText();

                        String typeProduct = Character.toString(pCode.charAt(0));


                        if (typeProduct.equals("L")){
                            Locomotive newLocomotive = new Locomotive(pCode,bName,g, pName,rPrice,sLevel,eCode, dCode);
                            databaseUpdateOps.addLocomotive(connection, newLocomotive);
                        }
                        else if (typeProduct.equals("S")){
                            RollingStock newRollingStock = new RollingStock(pCode,bName,g, pName,rPrice,sLevel,eCode);
                            databaseUpdateOps.addRollingStcok(connection, newRollingStock);
                        }
                        else if (typeProduct.equals("C")){
                            Controller newController = new Controller(pCode,bName,g, pName,rPrice,sLevel,tName);
                            databaseUpdateOps.addController(connection, newController);
                        }
                        else if (typeProduct.equals("M")){
                            Set newSet = new Set(pCode,bName,g, pName,rPrice,sLevel,null,cType);
                            databaseUpdateOps.addTrainSet(connection, newSet);
                        }
                        else {
                            databaseUpdateOps.addProduct(connection, newProduct);
                        }

                        reloadProducts(connection, databaseOperations);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                } else {
                    reloadProducts(connection, databaseOperations);
                }
            }
        });
    }
    
    /**
     * Add the metadata of each product onto the part of the screen they will be displayed
     * @param p the product being displayed
     * @return The panel displaying the product
     */
    public JPanel getProductSection(Product p, Connection connection){
        DatabaseOperations dbOps = new DatabaseOperations();
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        JPanel productSection = new JPanel();
        JPanel buttonPanel = new JPanel();
        editButton = new JButton ("Edit");
        deleteButton = new JButton ("Delete");

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
                productDisplay.append("\n " + ((Set) p).getEra() + "\n Controller Type: " +
                        ((Set) p).getControllerType());
        }
        productDisplay.append("\n £" + p.getRetailPrice());
        if (p.inStock())
            productDisplay.append("\n In stock");
        else
            productDisplay.append("\n Out of stock");
        productDisplay.setEditable(false);

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        productSection.add(productDisplay);
        productSection.add(buttonPanel);

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String typeProduct = Character.toString(p.getProductCode().charAt(0));

                JTextField productCode = new JTextField(p.getProductCode());
                productCode.setEnabled(false);

                JTextField brandName = new JTextField(p.getBrandName());
                JTextField productName = new JTextField(p.getProductName());
                JTextField retailPrice = new JTextField(String.valueOf(p.getRetailPrice()));
                JTextField gauge = new JTextField(p.getGauge());
                JTextField stockLevel = new JTextField(String.valueOf(p.getStockLevel()));

                //Considerations for Set
                setButton = new JButton("Set Details");
                JLabel sc = new JLabel("Add Products to Set (Optional):");

                setButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTextField productCodeField = new JTextField();
                        JLabel pc = new JLabel("Product Code:");
                       
                        JTextField quantityField = new JTextField();
                        JLabel qt = new JLabel("Product Quantity");
                        JPanel setPanel = new JPanel(new GridLayout(0, 1));
                        setPanel.add(pc);
                        setPanel.add(productCodeField);
                        setPanel.add(qt);
                        setPanel.add(quantityField);

                        int setResult = JOptionPane.showConfirmDialog(null, setPanel, "Set details",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (setResult == JOptionPane.OK_OPTION) {
                            try {
                                if (quantityField.getText().isBlank() || productCodeField.getText().isBlank()) {
                                    JOptionPane.showMessageDialog(setPanel, "Please fill fields",
                                            "Invalid Entry", 0);
                                }
                                else {
                                    int quantity = Integer.parseInt(quantityField.getText());
                                    String pCode = productCodeField.getText();
                                        try{
                                            if (p instanceof Set)
                                                dbUpdateOps.addToSet(connection, p.getProductCode(), pCode, quantity);
                                        } catch (Exception ee) {
                                            ee.printStackTrace();
                                        }
                                }
                            }
                            catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(setPanel, "Invalid quantity provided",
                                        "Invalid Entry", 0);
                            }
                        }
                    }
                });

                //For editing additional things in the specific category
                JTextField locEraCode = null;
                if (p instanceof Locomotive) {
                    locEraCode = new JTextField(((Locomotive)p).getEraCode());
                }
                JTextField rolEraCode = null;
                if (p instanceof RollingStock) {
                    rolEraCode = new JTextField(((RollingStock)p).getEraCode());
                }
                JTextField setEraCode = null;
                if (p instanceof Set) {
                    setEraCode = new JTextField(((Set)p).getEra());
                    setEraCode.setEnabled(false);
                }
                JLabel ec = new JLabel("Era Code:");

                JTextField dccCode = null;
                if (p instanceof Locomotive) {
                    dccCode = new JTextField(((Locomotive)p).getDccCode());
                }
                JLabel dc = new JLabel("Dcc Code:");

                JTextField typeName = new JTextField();
                JLabel tn = new JLabel("Type Name:") ;

                JTextField controllerType = null;
                if (p instanceof Set) {
                    controllerType = new JTextField(((Set)p).getEra());
                }
                JLabel ct = new JLabel("Controller Type:");

                JPanel panel = new JPanel(new GridLayout(0, 1));

                panel.add(new JLabel("Product Code"));
                panel.add(productCode);
                panel.add(new JLabel("Brand Name:"));
                panel.add(brandName);
                panel.add(new JLabel("Product Name:"));
                panel.add(productName);
                panel.add(new JLabel("Retail Price:"));
                panel.add(retailPrice);
                panel.add(new JLabel("Gauge:"));
                panel.add(gauge);
                panel.add(new JLabel("Stock Level:"));
                panel.add(stockLevel);

                switch(typeProduct){
                    case "L":
                        panel.add(ec);
                        panel.add(locEraCode);
                        panel.add(dc);
                        panel.add(dccCode);
                        break;
                    case "C":
                        panel.add(tn);
                        panel.add(typeName);
                        break;
                    case "S":
                        panel.add(ec);
                        panel.add(rolEraCode);
                        break;
                    case "M":
                        panel.add(ec);
                        panel.add(setEraCode);
                        panel.add(ct);
                        panel.add(controllerType);
                        panel.add(sc);
                        panel.add(setButton);
                        break;
                    case "P":
                        panel.add(sc);
                        panel.add(setButton);
                        break;
                }

                int result = JOptionPane.showConfirmDialog(null, panel, "Editing details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String bName = brandName.getText();
                        String pName = productName.getText();
                        double rPrice = Double.parseDouble(retailPrice.getText());
                        String g = gauge.getText();
                        int sLevel = Integer.parseInt(stockLevel.getText());

                        p.setProductName(pName);
                        p.setBrandName(bName);
                        p.setRetailPrice(rPrice);
                        p.setGauge(g);
                        if (sLevel < p.getStockLevel()){
                            JOptionPane.showMessageDialog(panel, "Stock Level low, Please increase from!" +
                                            p.getStockLevel(), "Invalid Entry", 0);
                        }
                        else {
                            p.setStockLevel(sLevel);
                        }

                        dbUpdateOps.editProductDetails(connection, p);

                        //Considerations for the specific types of products
                        String leCode = null;
                        if (locEraCode != null) {
                            leCode = locEraCode.getText();
                        }

                        String reCode = null;
                        if (rolEraCode != null) {
                            reCode = rolEraCode.getText();
                        }

                        String dCode = null;
                        if (dccCode != null) {
                            dCode = dccCode.getText();
                        }

                        String tName = typeName.getText();

                        String cType = null;
                        if (controllerType != null) {
                            cType = controllerType.getText();
                        }

                        if (p instanceof Locomotive) {
                            ((Locomotive)p).setEraCode(leCode);
                            ((Locomotive)p).setDccCode(dCode);
                            dbUpdateOps.editLocomotive(connection, ((Locomotive)p));
                        }

                        else if (p instanceof RollingStock){
                            ((RollingStock)p).setEraCode(reCode);
                            dbUpdateOps.editRollingStcok(connection, ((RollingStock)p));
                        }
                        else if (p instanceof Controller){
                            ((Controller)p).setTypeName(tName);
                            dbUpdateOps.editController(connection, ((Controller)p));
                        }
                        else if (p instanceof Set){
                            ((Set)p).setControllerType(cType);
                            dbUpdateOps.editTrainSet(connection, ((Set)p));
                        }
                        else {
                            dbUpdateOps.editProductDetails(connection, p);
                        }

                        reloadProducts(connection, dbOps);
                    } catch (SQLException ex){
                        ex.printStackTrace();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel, "Invalid details provided",
                                "Invalid Entry", 0);

                    }
                } else {
                    reloadProducts(connection, dbOps);
                }


            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dbUpdateOps.deleteProduct(connection, p);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(deleteButton, "Product Deleted");
                reloadProducts(connection, dbOps);
            }
        });

        return productSection;
    }

    public void reloadProducts(Connection connection, DatabaseOperations databaseOperations){
        setTitle("Trains of Sheffield (Staff) | Home");
        productPanel.removeAll();
        List<Product> products = null;
        try {
            products = databaseOperations.getProducts(connection);
            products.removeIf(n -> (n.getProductCode().isEmpty()));
            for (Product p: products) {
                if (!p.getProductCode().isEmpty())
                    productPanel.add(getProductSection(p, connection));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        revalidate();
        repaint();
    }

    public void reloadMyOrders(Connection connection, DatabaseOperations databaseOperations, Boolean displayAll) {
        setTitle("Trains of Sheffield (Staff) | Orders");
        addButton.setVisible(false);
    }
    public void reloadMyOrders(Connection connection, DatabaseOperations databaseOperations, boolean displayAll){
        setTitle("Trains of Sheffield (Staff) | Orders");

        productPanel.removeAll();
        BoxLayout layout = new BoxLayout(productPanel, BoxLayout.Y_AXIS);
        productPanel.setLayout(layout);
        List<Order> orders = null;
        try {
            if (!displayAll){
                // Display confirmed orders
                orders = databaseOperations.getOrders(connection, CurrentUserManager.getCurrentUser());
                orders.removeIf(n -> (n.getStatus() != OrderStatus.CONFIRMED));
                for (int i = 0; i < orders.size(); i++) {
                    productPanel.add(getOrderPanel(orders.get(i), connection, i));
                }
            } else {
                // Display fulfilled orders
                orders = databaseOperations.getOrders(connection, CurrentUserManager.getCurrentUser());
                orders.removeIf(n -> (n.getStatus() != OrderStatus.FULFILLED));
                Collections.reverse(orders);
                for (int i = 0; i < orders.size(); i++) {
                    productPanel.add(getOrderPanel(orders.get(i), connection, i));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        revalidate();
        repaint();
    }

    public JPanel getOrderPanel(Order o, Connection connection, int counter){
        DatabaseOperations dbOps = new DatabaseOperations();
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        User currentUser = CurrentUserManager.getCurrentUser();

        // Set the layout and border of the order section
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
                if (o.getStatus() == OrderStatus.CONFIRMED){
                    JButton fulfillButton = new JButton("Fulfill order");
                    fulfillButton.setMaximumSize(new Dimension(100,30));
                    fulfillButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (updateStockLevel(connection, o)){
                                o.setStatus(connection, OrderStatus.FULFILLED);
                            } else {
                                JOptionPane.showMessageDialog(newOrderPanel,"Order Blocked | Please decline order ",
                                        "Can't fullfil", 0);
                            }
                            JOptionPane.showMessageDialog(fulfillButton, "Order fulfilled");
                            reloadMyOrders(connection, dbOps, false);
                        }
                    });

                    JButton deleteButton = new JButton("Delete order");
                    deleteButton.setMaximumSize(new Dimension(100,30));
                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Delete order
                            for (OrderLine ol : o.getOrderLines()) {
                                try {
                                    dbUpdateOps.removeOrderLine(connection, ol);
                                    dbUpdateOps.removeOrder(connection, o);
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            JOptionPane.showMessageDialog(fulfillButton, "Order deleted");
                            reloadMyOrders(connection, dbOps, false);
                        }
                    });

                    if (counter == 0) {
                        buttonPanel.add(fulfillButton);
                        buttonPanel.add(deleteButton);
                    }
                    metadataPanel.add(buttonPanel);
                }

                metadataPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
                newOrderPanel.add(metadataPanel);

                // Add the products to the order section
                for (int i = 0; i < o.getOrderLines().size(); i++)
                    newOrderPanel.add(getOrderLine(connection, o.getOrderLines().get(i), o, i+1));

                JOptionPane.showMessageDialog(null, newOrderPanel, "Select Quantity", 1);
            }
        });

        return orderPanel;
    }

    public JPanel getOrderLine(Connection connection, OrderLine ol, Order o, int counter){
        DatabaseOperations dbOps = new DatabaseOperations();
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        JPanel orderLineSection = new JPanel();
        //Get the product represented in the orderLine
        Product lineProduct = ol.getProduct(connection);

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

        orderLineSection.add(orderlineDisplay);
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
                ("Orderline " + counter));
        title.setTitleJustification(TitledBorder.LEFT);
        orderLineSection.setBorder(title);
        return orderLineSection;
    }

    public boolean updateStockLevel(Connection connection, Order o) {
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        DatabaseOperations dbOps = new DatabaseOperations();
        for (OrderLine ol : o.getOrderLines()){
            try {
                Product product = ol.getProduct(connection);
                if (product.getStockLevel() >= ol.getProductQuantity()){
                    dbUpdateOps.updateStockLevel(connection, 0 - ol.getProductQuantity(), product);
                } else {
                    return false;
                }

                // If the product is a set, the stock levels of it's other products are decreased
                if (product instanceof Set){
                    List<Product> products = ((Set)product).getProductCodes();
                    List<Integer> productQuantities = dbOps.getComponentsQuantity(connection, (Set)product);
                    for (int i = 0 ; i < products.size(); i++){
                        if (products.get(i).getStockLevel() >= productQuantities.get(i)){
                            dbUpdateOps.updateStockLevel(connection, 0 - productQuantities.get(i),
                                    products.get(i));
                        } else {
                            dbUpdateOps.updateStockLevel(connection, ol.getProductQuantity(), product);
                            return false;
                        }

                        // If set is a train set, it will also contain a track pack
                        if (product instanceof Set){
                            List<Product> productsb = ((Set)product).getProductCodes();
                            List<Integer> productQuantitiesb = dbOps.getComponentsQuantity(connection,
                                    (Set)product);
                            for (int j = 0 ; j < productsb.size(); j++){
                                if (productsb.get(i).getStockLevel() >= productQuantitiesb.get(i)){
                                    dbUpdateOps.updateStockLevel(connection,
                                            0 - productQuantitiesb.get(j), productsb.get(j));
                                } else {
                                    dbUpdateOps.updateStockLevel(connection, productQuantities.get(i), products.get(i));
                                    return false;
                                }
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }
}


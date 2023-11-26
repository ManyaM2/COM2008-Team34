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
    private JButton addButton;
    private JPanel productPanel;
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

        JMenuItem detailsItem = new JMenuItem("View Orders");
        detailsItem.setMaximumSize((new Dimension(80,50)));

        menu = new JMenuBar();
        menu.add(prevItem);
        menu.add(homeItem);
        menu.add(productsMenu);
        menu.add(detailsItem);

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

        detailsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Trains of Sheffield (Staff) | Orders");
                productPanel.removeAll();
                GridLayout layout1 = new GridLayout(1,1);
                layout1.setHgap(5);
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
                revalidate();
                repaint();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField productCode = new JTextField("");
                JTextField brandName = new JTextField("");
                JTextField productName = new JTextField("");
                JTextField retailPrice = new JTextField("");
                JTextField gauge = new JTextField("");
                JTextField stockLevel = new JTextField("");
                JTextField partOfSetCode= new JTextField(null);
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
                int result = JOptionPane.showConfirmDialog(null, panel, "New Product Details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String pCode = productCode.getText();
                        String bName = brandName.getText();
                        String pName = productName.getText();
                        Double rPrice = Double.valueOf(retailPrice.getText());
                        String g = gauge.getText();
                        Integer sLevel = Integer.parseInt(stockLevel.getText());
                        String psCode = partOfSetCode.getText();
                        Product newProduct = new Product(pCode, bName, pName, rPrice, g, sLevel, psCode);
                        databaseUpdateOps.addProduct(connection, newProduct);
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
                List<Product> products = null;
                JTextField productCode = new JTextField(p.getProductCode());
                JTextField brandName = new JTextField(p.getBrandName());
                JTextField productName = new JTextField(p.getProductName());
                JTextField retailPrice = new JTextField(String.valueOf(p.getRetailPrice()));
                JTextField gauge = new JTextField(p.getGauge());
                JTextField stockLevel = new JTextField(String.valueOf(p.getStockLevel()));
                JTextField partOfSetCode= new JTextField(p.getProductCode());
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
                    int result = JOptionPane.showConfirmDialog(null, panel, "Editing details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String pCode = productCode.getText();
                        String bName = brandName.getText();
                        String pName = productName.getText();
                        Double rPrice = Double.valueOf(retailPrice.getText());
                        String g = gauge.getText();
                        Integer sLevel = Integer.parseInt(stockLevel.getText());
                        String psCode = partOfSetCode.getText();
                        p.setProductCode(pCode);
                        p.setProductName(pName);
                        p.setBrandName(bName);
                        p.setRetailPrice(rPrice);
                        p.setGauge(g);;
                        p.setStockLevel(sLevel);
                        dbUpdateOps.editProductDetails(connection, p);
                        reloadProducts(connection, dbOps);
                    } catch (Exception ex){
                        ex.printStackTrace();
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
                "\n " + o.getDateMade() + "\n"  );

        buttonPanel.add(fulfillButton);
        buttonPanel.add(deleteButton);

        //Add button to order the product
        buttonPanel.add(selectButton);
        orderSection.add(orderDisplay);
        orderSection.add(buttonPanel);
        return orderSection;
    }
}


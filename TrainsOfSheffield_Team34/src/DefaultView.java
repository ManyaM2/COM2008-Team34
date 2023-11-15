import javax.swing.*;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DefaultView extends JFrame {
    private JMenuBar menu;
    private JButton staffArea;
    private JTextArea product;


    public DefaultView(Connection connection) throws SQLException {
        this.setTitle("Trains of Sheffield | Home");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize.width/2, screenSize.height/2);
        setLocation(screenSize.width/4, screenSize.height/4);

        JPanel panel = new JPanel();

        JMenu homeMenu = new JMenu ("Home");
        JMenu productsMenu = new JMenu ("Products");
        JMenuItem locomotivesItem = new JMenuItem ("Locomotives");
        productsMenu.add (locomotivesItem);
        JMenuItem track_packsItem = new JMenuItem ("Track Packs");
        productsMenu.add (track_packsItem);
        JMenuItem train_setsItem = new JMenuItem ("Train Sets");
        productsMenu.add (train_setsItem);
        JMenuItem controllersItem = new JMenuItem ("Controllers");
        productsMenu.add (controllersItem);
        JMenuItem track_piecesItem = new JMenuItem ("Track Pieces");
        productsMenu.add (track_piecesItem);
        JMenuItem rolling_stockItem = new JMenuItem ("Rolling Stock");
        productsMenu.add (rolling_stockItem);
        JMenu profileMenu = new JMenu ("Profile");
        JMenuItem edit_detailsItem = new JMenuItem ("Edit Details");
        profileMenu.add (edit_detailsItem);
        JMenuItem my_ordersItem = new JMenuItem ("My Orders");
        profileMenu.add (my_ordersItem);

        //construct components
        menu = new JMenuBar();
        menu.add (homeMenu);
        menu.add (productsMenu);
        menu.add (profileMenu);
        staffArea = new JButton ("Staff");



        //add components
        panel.add(menu);
        panel.add(staffArea);

        //set component bounds (only needed by Absolute Positioning)
        menu.setBounds (0, 0, 755, 30);
        staffArea.setBounds (680, 40, 65, 30);

        //construct components
        product = new JTextArea (10, 20);

        //adjust size and set layout
        setPreferredSize (new Dimension (752, 431));
        FlowLayout layout = new FlowLayout();
        layout.setHgap (5);
        layout.setVgap (5);
        setLayout (layout);

        //adjust size and set layout
        setPreferredSize (new Dimension (752, 431));
        setLayout (new BoxLayout(panel, BoxLayout.Y_AXIS));

        //add components
        panel.add(product);
        add(panel);

        this.setVisible(true);
    }
}

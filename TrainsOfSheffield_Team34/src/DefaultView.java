import javax.swing.*;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DefaultView extends JFrame {
    public DefaultView(Connection connection) throws SQLException {
        this.setTitle("Trains of Sheffield | Home");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize.width/2, screenSize.height/2);
        setLocation(screenSize.width/4, screenSize.height/4);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JMenuBar navBar = new JMenuBar();
        JMenu home = new JMenu("Home");
        JMenu products = new JMenu("Products");
        JMenuItem locos = new JMenuItem("Locomotives");
        JMenuItem rollingStock = new JMenuItem("Rolling Stock");
        JMenuItem track = new JMenuItem("Track Pieces");
        JMenuItem controllers = new JMenuItem("Controllers");
        JMenuItem trainSets = new JMenuItem("Train Sets");
        JMenuItem trackPacks = new JMenuItem("Track Packs");
        products.add(locos);
        products.add(rollingStock);
        products.add(track);
        products.add(controllers);
        products.add(trainSets);
        products.add(trackPacks);
        contentPane.add(navBar);
        this.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AddOrderActionListener implements ActionListener {
    private Product product;
    private JFrame currentView;

    private Connection connection;

    public AddOrderActionListener(Product p, JFrame view, Connection c) {
        product = p;
        currentView = view;
        connection = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (product.inStock()){
            try{
                //Add orderLine to the database
                DbUpdateOperations updateOperations = new DbUpdateOperations();
                updateOperations.addOrderLine(connection, product);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(currentView, product.getProductCode() + " Added to your order." +
                    " \n To view go to Profile - My Orders");
        } else {
            JOptionPane.showMessageDialog(currentView,product.getProductCode() + " Out of stock. \n We are" +
                    " unable to add it to your order","Error", 0);
        }
    }
}

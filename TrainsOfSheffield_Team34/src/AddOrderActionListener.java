import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class AddOrderActionListener implements ActionListener {
    private Product product;
    private JFrame currentView;

    private Connection connection;

    private OrderLine newOrderLine;

    private JPanel panel;

    public AddOrderActionListener(Product p, JFrame view, Connection c) {
        product = p;
        currentView = view;
        connection = c;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DbUpdateOperations updateOperations = new DbUpdateOperations();
        newOrderLine = new OrderLine(1, 1, product.getProductCode());
        if (product.inStock()){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            try{
                //Add orderLine to the database
                newOrderLine = updateOperations.addOrderLine(connection, newOrderLine);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Update orderline quantity based on the spinner value
            JSpinner selectQuantity = null;
            selectQuantity = new JSpinner(new SpinnerNumberModel(1, 1,
                    product.getStockLevel(), 1));

            JLabel qLabel = new JLabel("Edit quantity: ");
            JSpinner finalSelectQuantity = selectQuantity;
            selectQuantity.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    newOrderLine.setProductQuantity((int)finalSelectQuantity.getValue());
                    try {
                        updateOperations.updateOrderLineQuantity(connection, newOrderLine);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            panel.add(qLabel);
            panel.add(finalSelectQuantity);
            int result = JOptionPane.showConfirmDialog(null, panel, "Select Quantity",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(currentView, product.getProductCode() + " Added to your order." +
                        " \n To view go to Profile - My Orders");
            } else {
                // Remove the orderline that was just added if cancel is selected
                try {
                    updateOperations.removeOrderLine(connection, newOrderLine);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(currentView,product.getProductCode() + " Out of stock. \n We are" +
                    " unable to add it to your order","Error", 0);
        }
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddOrderActionListener implements ActionListener {
    private Product product;
    private JFrame currentView;

    public AddOrderActionListener(Product product, JFrame defaultView) {
        this.product = product;
        currentView = defaultView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(currentView, product.getProductCode() + " Added to your order." +
                " \n To view go to Profile - My Orders");
        /**
         * Add orderline to pending order, if there are no pending orders, create a new order
         */
    }
}

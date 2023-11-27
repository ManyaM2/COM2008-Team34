import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderLine {
    private int productQuantity;
    private int lineNumber;
    private String productCode;
    
    // Constructor
    public OrderLine(int productQuantity, int lineNumber, String productCode) {
        this.productQuantity = productQuantity;
        this.lineNumber = lineNumber;
        this.productCode = productCode;
    }

    public OrderLine(int productQuantity, String productCode) {
        this.productQuantity = productQuantity;
        this.productCode = productCode;
    }

    /**
     * Get the product described by the orderline
     * @param connection
     * @return
     */
    public Product getProduct(Connection connection) {
        DatabaseOperations dbOps = new DatabaseOperations();
        Product product = null;
        try {
            List<Product> products = dbOps.getProducts(connection);
            for (Product p : products){
                if (p.getProductCode().equals(productCode) && p != null)
                    product = p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }
    
    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int q) {
        productQuantity = q;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getProductCode(){ return  productCode; }

    public void setLineNumber(int ln){ lineNumber = ln; }

    public double lineCost(Connection connection) {
        return productQuantity * getProduct(connection).getRetailPrice();
    }
}

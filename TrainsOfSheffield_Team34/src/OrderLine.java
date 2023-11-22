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

    public float lineCost(int quantity, float price) {
        return quantity * price;
    }
}

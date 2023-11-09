public class OrderLine {
    private int productQuantity;
    private int lineNumber;
    
    // Constructor
    public OrderLine(int productQuantity, int lineNumber) {
        this.productQuantity = productQuantity;
        this.lineNumber = lineNumber;
    }
    
    public int getProductQuantity() {
        return productQuantity;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public float lineCost(int quantity, float price) {
        return quantity * price;
    }
}

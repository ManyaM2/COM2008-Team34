public class Product {
    private String brandName;
    private String gauge;
    private String productName;
    private String productCode;
    private double retailPrice;
    private int stockLevel;

    public String getBrandName() { return brandName; }
    public String getGauge() { return gauge; }
    public String getProductName() { return productName; }
    public String getProductCode() { return productCode; }
    public double getRetailPrice() { return retailPrice; }
    public int getStockLevel() { return stockLevel; }
    public String getScale(){
        String scale = "";
        switch (gauge){
            case "OO" -> scale = "1/76th";
            case "TT" -> scale = "1/120th";
            case "N" -> scale = "1/148th";
            default -> scale = "null";
        }
        return scale;
    }

    public Product(String bName, String gauge, String pName, double rPrice, int sLevel){
        this.brandName = bName;
        this.gauge = gauge;
        this.productName = pName;
        this.retailPrice = rPrice;
        this.stockLevel = sLevel;
    }

    /**
     * Increment/decrement the stock level of a product depending on the stockNum
     * @param stockNum Amount incremented/decremented - Postive integer increments, negative integer decrements
     */
    public void updateStock(int stockNum) { stockLevel += stockNum; }

    /**
     * @return True if a product is in stock
     */
    public boolean inStock(){
        if (stockLevel > 0)
            return true;
        return false;
    }
}

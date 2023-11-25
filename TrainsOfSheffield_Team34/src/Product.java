public class Product {
    private String brandName;
    private String gauge;
    private String productName;
    private String productCode;
    private double retailPrice;
    private String partOfSetCode;
    private int stockLevel;


    public String getBrandName() { return brandName; }
    public String getGauge() { return gauge; }
    public String getProductName() { return productName; }
    public String getProductCode() { return productCode; }
    public double getRetailPrice() { return retailPrice; }
    public int getStockLevel() { return stockLevel; }
    public String getPartOfSetCode() {return partOfSetCode; } ;
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

    public void setProductCode(String fn){ productCode = fn; }
    public void setProductName(String fn){ productName = fn; }
    public void setBrandName(String e){ brandName = e; }
    public void setGauge(String g) { gauge = g;}

    public void setRetailPrice(double rPrice) { retailPrice = rPrice;}
    public void setStockLevel(int sLevel) {stockLevel = sLevel;}

    public Product(String pCode, String bName, String pName, double rPrice, String gauge, int sLevel, String psCode){
        this.productCode = pCode;
        this.brandName = bName;
        this.productName = pName;
        this.retailPrice = rPrice;
        this.gauge = gauge;
        this.stockLevel = sLevel;
        this.partOfSetCode = psCode;
    }

    /**
     * Increment/decrement the stock level of a product depending on the stockNum
     * @param stockNum Amount incremented/decremented - Postive integer increments, negative integer decrements
     */
    public void updateStock(int stockNum) {
        stockLevel += stockNum;
    }

    /**
     * @return True if a product is in stock
     */
    public boolean inStock(){
        if (stockLevel > 0)
            return true;
        return false;
    }
}

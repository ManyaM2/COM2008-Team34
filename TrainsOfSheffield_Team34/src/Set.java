import java.util.List;

public class Set extends Product{
    private List<Product> productCodes;

    public List<Product> getProductCodes(){ return productCodes; }
    public Set(String bName, String gauge, String pName, double rPrice, int sLevel, List<Product> products) {
        super(bName, gauge, pName, rPrice, sLevel);
        this.productCodes = products;
    }

    /**
     * Gets the era code of a set depending on the era of it's contained locomotive(s)
     * @return "null" if it is a track pack, the era as a String otherwise
     */
    public String getEra(){
        String era = "null";
        for (Product product : productCodes){
            if (product.getProductCode().charAt(0) == 'L')
                era = ((Locomotive) product).getEraCode();
        }
        return era;
    }
}

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Set extends Product{
    private List<Product> productCodes;
    private int setID;
    private String controllerType;


    public int getSetID(){return setID;}
    public String getControllerType(){return controllerType;}
    public List<Product> getProductCodes(){ return productCodes; }
    public Set(String pCode, String bName, String gauge, String pName, double rPrice, int sLevel, List<Product> products,
               String controllerType) {
        super(pCode, bName, pName, rPrice, gauge, sLevel);
        this.productCodes = products;
        this.controllerType = controllerType;
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

    /**
     * Returns a string of all the products in the set -- Example: "4x R1234 Single Straight"
     * @param connection the database connection
     * @return
     */
    public String productsAsString(Connection connection){
        DatabaseOperations dbOps = new DatabaseOperations();
        String returnString = "";
        try {
            List<Integer> pQuanities = dbOps.getComponentsQuantity(connection, this);
            for (int i = 0; i < productCodes.size(); i++){
                Product p = productCodes.get(i);
                int pAmount = pQuanities.get(i);
                returnString += pAmount + "x " + p.getProductCode() + " " + p.getProductName() + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnString;
    }
}

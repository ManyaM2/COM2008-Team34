public class RollingStock extends Product{
    private String eraCode;

    public String getEraCode(){ return eraCode; }
    public RollingStock(String bName, String gauge, String pName, double rPrice, int sLevel, String era, String dcc) {
        super(bName, gauge, pName, rPrice, gauge, sLevel);
        this.eraCode = era;
    }
}

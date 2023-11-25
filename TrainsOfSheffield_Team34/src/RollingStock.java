public class RollingStock extends Product{
    private String eraCode;

    public String getEraCode(){ return eraCode; }
    public RollingStock(String pCode, String bName, String gauge, String pName, double rPrice, int sLevel, String psCode, String era) {
        super(pCode, bName, pName, rPrice, gauge, sLevel, psCode);
        this.eraCode = era;
    }
}

public class Locomotive extends Product{
    private String eraCode;
    private String dccCode;

    public String getEraCode(){ return eraCode; }
    public String getDccCode(){ return dccCode; }
    public void setEraCode(String e) { eraCode = e;}
    public void setDccCode(String d) { dccCode = d;}
    public Locomotive(String pCode, String bName, String gauge, String pName, double rPrice, int sLevel,
                      String era, String dcc) {
        super(pCode, bName, pName, rPrice, gauge, sLevel);
        this.eraCode = era;
        this.dccCode = dcc;
    }
}

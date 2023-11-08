import java.util.List;

public class Locomotive extends Product{
    private String eraCode;
    private String dccCode;

    public String getEraCode(){ return eraCode; }
    public String getDccCode(){ return dccCode; }
    public Locomotive(String bName, String gauge, String pName, double rPrice, int sLevel, String era, String dcc) {
        super(bName, gauge, pName, rPrice, sLevel);
        this.eraCode = era;
        this.dccCode = dcc;
    }
}

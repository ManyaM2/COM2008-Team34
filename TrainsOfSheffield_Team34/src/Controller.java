public class Controller extends Product{
    private String typeName;
    public String getTypeName(){ return typeName; }
    public Controller(String bName, String gauge, String pName, double rPrice, int sLevel, String type) {
        super(bName, gauge, pName, rPrice, sLevel);
        this.typeName = type;
    }
}

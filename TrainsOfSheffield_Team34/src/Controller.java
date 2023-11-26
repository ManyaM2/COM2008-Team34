public class Controller extends Product{
    private String typeName;

    public String getTypeName(){ return typeName; }
    public void setTypeName(String tp) { typeName = tp;}

    public Controller(String pCode, String bName, String gauge, String pName, double rPrice,
                      int sLevel, String typeName) {
        super(pCode, bName, pName, rPrice, gauge, sLevel);
        this.typeName = typeName;
    }
}

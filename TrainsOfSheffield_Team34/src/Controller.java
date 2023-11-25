public class Controller extends Product{
    private String typeName;

    public String getTypeName(){ return typeName; }

    public Controller(String pCode, String bName, String gauge, String pName, double rPrice,
                      int sLevel,String psCode, String typeName) {
        super(pCode, bName, pName, rPrice, gauge, sLevel, psCode);
        this.typeName = typeName;
    }
}

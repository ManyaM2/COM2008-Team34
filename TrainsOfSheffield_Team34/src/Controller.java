public class Controller extends Product{
    private Integer controllerID;
    private String typeName;

    public String getTypeName(){ return typeName; }
    public Controller(Integer controllerID,String bName, String gauge, String pName, double rPrice, int sLevel, String type) {
        super(bName, gauge, pName, rPrice, gauge, sLevel);
        this.controllerID = controllerID;
        this.typeName = type;
    }
}

public class Controller extends Product{
    private Integer controllerID;
    private String typeName;

    public String getTypeName(){ return typeName; }
    public int getControllerID(){ return controllerID; }
    public Controller(int controllerID,String bName, String gauge, String pName, double rPrice, int sLevel, String typeName) {
        super(bName, gauge, pName, rPrice, gauge, sLevel);
        this.controllerID = controllerID;
        this.typeName = typeName;
    }
}

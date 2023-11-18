public class Controller extends Product{
    private Integer controllerID;
    private String typeName;

    public String getTypeName(){ return typeName; }
    public int getControllerID(){ return controllerID; }
    public Controller(String pCode, int controllerID,String bName, String gauge, String pName, double rPrice,
                      int sLevel, String typeName) {
        super(pCode, bName, gauge, pName, rPrice, sLevel);
        this.controllerID = controllerID;
        this.typeName = typeName;
    }
}

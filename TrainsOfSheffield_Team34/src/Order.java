import java.util.List;
import java.time.LocalDate;

public class Order {
    private Integer orderNumber;
    private Integer userID;
    private OrderStatus status;
    private List<OrderLine> orderLines;
    private Integer quantity;
    private float price;

    private LocalDate dateMade;

    // Constructor
    public Order(Integer orderNumber, Integer userID, OrderStatus status, List<OrderLine> orderLines) {
        this.orderNumber = orderNumber;
        this.userID = userID;
        this.status = status;
        this.orderLines = orderLines;
    }
    
    public Integer getOrderNumber() {
        return orderNumber;
    }
   
    public Integer getUserID() {
        return userID;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public LocalDate getDateMade(){ return dateMade; }

    public void setStatus(OrderStatus s){ status = s; }
    
    public void saveDate() {
        dateMade = LocalDate.now();
    }
    
    public float totalCost(List<OrderLine> lines) {
        float total = 0.0f;
        for (OrderLine line : lines) {
            total += line.lineCost(quantity,price);
        }
        return total;
    }
}







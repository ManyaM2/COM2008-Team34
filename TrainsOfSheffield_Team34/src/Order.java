import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDate;

public class Order {
    private Integer orderNumber;
    private Integer userID;
    private OrderStatus status;
    private List<OrderLine> orderLines;
    private LocalDate dateMade;

    // Constructor
    public Order(Integer orderNumber, Integer userID, OrderStatus status, List<OrderLine> orderLines, LocalDate date) {
        this.orderNumber = orderNumber;
        this.userID = userID;
        this.status = status;
        this.orderLines = orderLines;
        this.dateMade = date;
    }
    
    public Integer getOrderNumber() {
        return orderNumber;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public LocalDate getDateMade(){ return dateMade; }

    /**
     * Set the status of the order instance and the order in the database to s
     * @param connection
     * @param s The new status of the order
     */
    public void setStatus(Connection connection, OrderStatus s){
        DbUpdateOperations dbUpdateOps = new DbUpdateOperations();
        status = s;
        try {
            dbUpdateOps.updateOrderStatus(connection, this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public double totalCost(Connection connection) {
        float total = 0.0f;
        for (OrderLine line : orderLines) {
            total += line.lineCost(connection);
        }
        return total;
    }
}







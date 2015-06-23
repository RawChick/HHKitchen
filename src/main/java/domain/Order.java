package domain;



/**
 * 
 * This class contains the attributes for the orders and their respective getters and setters.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @see businesslogic.OrderManager
 * @version 1.0
 * 
 */

public class Order {
    private int tableNr;
    private int orderNr;
    private int status;


    /**
     * 
     * @param tableNr Table number from an order.
     * @param orderNr Number of an order.
     * @param status State of an order.
     */
    public Order(int tableNr, int orderNr, int status) {
        this.tableNr = tableNr;
        this.orderNr = orderNr;
        this.status = status;
    }

    public int getTableNr() {
        return tableNr;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

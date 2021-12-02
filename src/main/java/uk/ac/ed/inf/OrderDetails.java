package uk.ac.ed.inf;
import java.util.ArrayList;

/**
 * OrderDetails class stores information about an order
 */
public class OrderDetails {
    private String orderNo;
    private String deliveryDate;
    private ArrayList<Item> items;
    private String customer;
    private LongLat deliverTo;
    private String what3Words;

    /**
     * simple constructor directly stores the parameters passed in an OrderDetails object
     * @param orderNo the number of the order placed
     * @param deliveryDate the date of the order placed
     * @param customer the customer Id number
     * @param deliverTo the LongLat coords of the customer location
     * @param what3Words the what3words address of the customer location
     */
    public OrderDetails(String orderNo, String deliveryDate, String customer, LongLat deliverTo, String what3Words) {
    this.deliveryDate = deliveryDate;
    this.customer = customer;
    this.deliverTo = deliverTo;
    this.orderNo = orderNo;
    this.items = new ArrayList<>();
    this.what3Words = what3Words;

    }

    public String getOrderNo() {
        return orderNo;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public LongLat getDeliverTo() {
        return deliverTo;
    }

    public String getWhat3Words() {
        return what3Words;
    }
}

package uk.ac.ed.inf;
import java.util.ArrayList;

/**
 * OrderDetails class stores information about an order
 */
public class OrderDetails {//todo investigate Date type and LongLat type for these vals
    private String orderNo;
    private String deliveryDate;
    private ArrayList<Item> items;
    private String customer;
    private LongLat deliverTo;
    private String what3Words;

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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getCustomer() {
        return customer;
    }

    public LongLat getDeliverTo() {
        return deliverTo;
    }

    public String getWhat3Words() {
        return what3Words;
    }
}

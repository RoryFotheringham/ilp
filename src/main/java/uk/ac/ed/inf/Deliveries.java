package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * class used to store delivery information in the form that is written to the database
 * and keep track of deliveries that have been made by the drone
 */
public class Deliveries {
    private static final int DELIVERY_COST = 50;
    private HashMap<String, DeliveryDetails> deliveryMap = new HashMap<>();
    private ArrayList<DeliveryDetails> completedDeliveries = new ArrayList<>();
    private ArrayList<DeliveryDetails> allDeliveries = new ArrayList<>();

    /**
     * Constructor stores all of the orders placed in a list and map
     * @param orderDetails information on all of the orders placed
     */
    public Deliveries(ArrayList<OrderDetails> orderDetails){
        storeDeliveries(orderDetails);
    }

    public ArrayList<DeliveryDetails> getAllDeliveries() {
        return allDeliveries;
    }

    public ArrayList<DeliveryDetails> getCompletedDeliveries() {
        return completedDeliveries;
    }

    /**
     * iterates through every entry in orderDetails and adds it to a HashMap(orderNo, DeliveryDetails)
     * as well as an ArrayList(DeliveryDetails)
     * which is stored in private instance variables - deliveryMap and allDeliveries respectively
     * @param orderDetails a list of every order made for the given date.
     */
    public void storeDeliveries(ArrayList<OrderDetails> orderDetails){
        for(OrderDetails orderDetail: orderDetails){
            String orderNo = orderDetail.getOrderNo();
            String deliveredTo = orderDetail.getWhat3Words();
            int totalCost = DELIVERY_COST;
            for(Item item: orderDetail.getItems()){
                totalCost += item.getPence();
            }
            DeliveryDetails deliveryDetails = new DeliveryDetails(orderNo, deliveredTo, totalCost);
            deliveryMap.put(orderNo, deliveryDetails);
            allDeliveries.add(deliveryDetails);
        }
    }

    /**
     * The DeliveryDetails object which corresponds to the given orderNo parameter is added to the instance variable madeDeliveries.
     * @param orderNo an order number.
     */
    public void makeDelivery(String orderNo) {
        if(!deliveryMap.containsKey(orderNo)){
            throw new IllegalArgumentException("delivery number not recognised");
        }
        DeliveryDetails deliveryDetails = deliveryMap.get(orderNo);
        completedDeliveries.add(deliveryDetails);
    }
}

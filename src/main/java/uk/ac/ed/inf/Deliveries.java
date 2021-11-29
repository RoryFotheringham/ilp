package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.HashMap;

public class Deliveries {
    private static final int DELIVERY_COST = 50;
    private HashMap<String, DeliveryDetails> deliveryMap = new HashMap<>();
    private ArrayList<DeliveryDetails> completedDeliveries = new ArrayList<>();

    public Deliveries(ArrayList<OrderDetails> orderDetails){
        makeDeliveryMap(orderDetails);
    }

    /**
     * iterates through every entry in orderDetails and adds it to a HashMap<orderNo, DeliveryDetails>
     * which is stored in private instance variable - deliveryMap
     * @param orderDetails a list of every order made for the given date.
     */
    public void makeDeliveryMap(ArrayList<OrderDetails> orderDetails){
        for(OrderDetails orderDetail: orderDetails){
            String orderNo = orderDetail.getOrderNo();
            String deliveredTo = orderDetail.getWhat3Words();
            int totalCost = DELIVERY_COST;
            for(Item item: orderDetail.getItems()){
                totalCost += item.getPence();
            }
            DeliveryDetails deliveryDetails = new DeliveryDetails(orderNo, deliveredTo, totalCost);
            deliveryMap.put(orderNo, deliveryDetails);
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
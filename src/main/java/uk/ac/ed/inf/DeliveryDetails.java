package uk.ac.ed.inf;

/**
 * class stores information about each delivery made - eventually this data is written to the database
 */
public class DeliveryDetails {
    private String orderNo;
    private String deliveredTo;
    private int costInPence;

    public DeliveryDetails(String orderNo, String deliveredTo, int costInPence) {
        this.orderNo = orderNo;
        this.deliveredTo = deliveredTo;
        this.costInPence = costInPence;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getDeliveredTo() {
        return deliveredTo;
    }

    public int getCostInPence() {
        return costInPence;
    }
}

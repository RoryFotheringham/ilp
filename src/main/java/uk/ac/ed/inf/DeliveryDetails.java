package uk.ac.ed.inf;

/**
 * class stores information about each delivery made - eventually this data is written to the database
 */
public class DeliveryDetails {
    private String orderNo;
    private String deliveredTo;
    private int costInPence;

    /**
     * constructor stores the information passed to it
     * @param orderNo the order number
     * @param deliveredTo the what3words string describing the customer location
     * @param costInPence the cost of the order including delivery cost
     */
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

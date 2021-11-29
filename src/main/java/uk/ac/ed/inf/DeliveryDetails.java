package uk.ac.ed.inf;

public class DeliveryDetails {
    String orderNo;
    String deliveredTo;
    int costInPence;

    public DeliveryDetails(String orderNo, String deliveredTo, int costInPence) {
        this.orderNo = orderNo;
        this.deliveredTo = deliveredTo;
        this.costInPence = costInPence;
    }
}

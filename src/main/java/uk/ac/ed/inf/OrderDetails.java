package uk.ac.ed.inf;

import java.sql.Date;
import java.util.ArrayList;

public class OrderDetails {//todo investigate Date type and LongLat type for these vals
    String orderNo;
    String deliveryDate;
    ArrayList<Item> items;
    String customer;
    LongLat deliverTo;
    String what3Words;

public OrderDetails(String orderNo, String deliveryDate, String customer, LongLat deliverTo, String what3Words) {
    this.deliveryDate = deliveryDate;
    this.customer = customer;
    this.deliverTo = deliverTo;
    this.orderNo = orderNo;
    this.items = new ArrayList<>();
    this.what3Words = what3Words;

    }

}

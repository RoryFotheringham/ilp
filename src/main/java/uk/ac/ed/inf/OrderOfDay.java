package uk.ac.ed.inf;

import java.util.Date;
import java.util.HashMap;

public class OrderOfDay {
    String orderNo;
    Date deliveryDate;
    String customer;
    String deliverTo; //todo check what type this should really be
    HashMap<String, OrderDetails> detailsHashMap;
}

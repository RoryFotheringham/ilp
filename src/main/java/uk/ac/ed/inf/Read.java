package uk.ac.ed.inf;

import java.sql.Date;
import java.util.HashMap;

public class Read {
    private Orders orders;
    private Deliveries deliveries;
    private Area area;

    public Read(String machineName, String portWeb, String portDB, Date date){
        Menus menus = new Menus(machineName, portWeb);
        HashMap<String, Item> itemMap = menus.getItemMap();
        Orders orders = new Orders(machineName, portWeb, portDB, date, itemMap);
        Deliveries deliveries = new Deliveries(orders.getOrdersList());
        Area area = new Area(machineName, portWeb);

        this.orders = orders;
        this.deliveries = deliveries;
        this.area = area;
    }

    public Orders getOrders() {
        return orders;
    }

    public Deliveries getDeliveries() {
        return deliveries;
    }

    public Area getArea() {
        return area;
    }
}

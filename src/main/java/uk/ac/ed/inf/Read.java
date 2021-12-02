package uk.ac.ed.inf;

import java.sql.Date;
import java.util.HashMap;

/**
 * high level class handles the reading and storing of data from the database and web server
 */
public class Read {
    private Orders orders;
    private Deliveries deliveries;
    private Area area;

    /**
     * high level class handles the reading and storing of data from the database and web server
     * @param machineName machine name
     * @param portWeb the port number of the web server
     * @param portDB the port of the database server
     * @param date the date for which the class is storing information on in java.sql.Date format
     */
    public Read(String machineName, String portWeb, String portDB, Date date){
        Menus menus = new Menus(machineName, portWeb); //read and store menus info from the web server
        HashMap<String, Item> itemMap = menus.getItemMap();
        Orders orders = new Orders(machineName, portWeb, portDB, date, itemMap); // read and store orders info from the database
        Deliveries deliveries = new Deliveries(orders.getOrdersList());
        Area area = new Area(machineName, portWeb); // read and store Area info from the web server (No fly zones and Landmarks)

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

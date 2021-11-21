package uk.ac.ed.inf;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;

public class App
{
    public static void main( String[] args ){//todo get rid of the sql exception and handle it in the class
    String portDB = "1527";
    String portWeb = "9898";
    String machineName = "localhost";
    Date date = Date.valueOf("2023-03-31"); //placeholder params
//todo handle the args to construct Menus and Orders Classes


        Menus menus = new Menus(machineName, portWeb);
        HashMap<String, Item> itemMap = menus.getItemMap();
        Orders orders = new Orders(machineName, portWeb, portDB, date, itemMap);
        Area area = new Area(machineName, portWeb);
        OrderDetails testOrder = orders.ordersList.get(0);
        Graph graph = new Graph(area, testOrder);
    }
}

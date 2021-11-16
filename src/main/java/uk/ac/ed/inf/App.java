package uk.ac.ed.inf;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;

public class App
{
    public static void main( String[] args ) throws SQLException {
    String dbPort = "1527";
    String webPort = "9898";
    String machineName = "localhost";
    Date date = Date.valueOf("2023-03-31"); //placeholder params


//todo handle the args to construct Menus and Orders Classes
        Menus menus = new Menus(machineName, webPort);
        Orders orders = new Orders(machineName, dbPort, date, menus.getItemMap());

        System.out.println(orders.ordersList.get(0).customer);
    }
}

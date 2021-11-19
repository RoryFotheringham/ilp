package uk.ac.ed.inf;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;

public class App
{
    public static void main( String[] args ) throws SQLException {
    String portDB = "1527";
    String portWeb = "9898";
    String machineName = "localhost";
    Date date = Date.valueOf("2023-03-31"); //placeholder params
//todo handle the args to construct Menus and Orders Classes


        Menus menus = new Menus(machineName, portWeb);
        Orders orders = new Orders(machineName, portWeb, portDB, date, menus.getItemMap());
        Area area = new Area(machineName, portWeb);
        Graph graph = new Graph(area);
    }
}

package uk.ac.ed.inf;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class App
{
    public static void main( String[] args ){//todo get rid of the sql exception and handle it in the class
    String portDB = "1527";
    String portWeb = "9898";
    String machineName = "localhost";
    Date date = Date.valueOf("2023-11-11"); //placeholder params
//todo handle the args to construct Menus and Orders Classes


        Menus menus = new Menus(machineName, portWeb);
        HashMap<String, Item> itemMap = menus.getItemMap();
        Orders orders = new Orders(machineName, portWeb, portDB, date, itemMap);
        Deliveries deliveries = new Deliveries(orders.getOrdersList());
        Area area = new Area(machineName, portWeb);
        NoFlyPolice nfp = new NoFlyPolice(area);
        Graph graph = new Graph(area, orders);
        PathManagement pathManagement = new PathManagement(graph, orders);

        ArrayList<Node> absPathList = new ArrayList<>();
        absPathList.addAll(pathManagement.getAbsolutePath().getPathList());
        Path absPath = new Path(absPathList, 0);
        FlightPath flightPath = new FlightPath(graph, pathManagement.getAbsolutePath(), deliveries, area);
        if(nfp.intersectsNoFly(absPath.getPathList())){
            System.out.println("br");
        }

        if(area.intersectsNoFly(new LongLat(-3.1860995292663574, 55.94466399121498), new LongLat(-3.190991878509521, 55.94558922395267))){
            System.out.println("br");
        }
        Visualise vis = new Visualise(flightPath.getFlightPath(), "01-01-2023", absPath.getPathList());
    }
}

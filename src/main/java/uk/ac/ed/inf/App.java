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
    Date date = Date.valueOf("2023-12-12"); //placeholder params
    String ddMMYYYY = ("12-12-2023");
//todo handle the args to construct Menus and Orders Classes

        //read information from the web server and database
        Read read = new Read(machineName, portWeb, portDB, date);
        Orders orders = read.getOrders();
        Area area = read.getArea();
        Deliveries deliveries = read.getDeliveries();
        //process the information into a flightPath and absolutePath
        Process process = new Process(orders, deliveries, area);
        ArrayList<Node> absolutePathList = process.getAbsPathList();
        FlightPath flightPath = process.getFlightPath();
        //create geoJson file from flightPath and absolutePath
        Write write = new Write(flightPath, absolutePathList, deliveries, ddMMYYYY, machineName, portDB);
        EvaluateViability eval = new EvaluateViability(machineName, portDB, portWeb);
    }
}

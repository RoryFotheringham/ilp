package uk.ac.ed.inf;

import java.util.ArrayList;

public class Write {
    public Write(FlightPath flightPath, ArrayList<Node> absPathList, Deliveries deliveries, String ddMMYYYY, String machineName, String portDB){
        Visualise vis = new Visualise(flightPath.getFlightPath(), "01-01-2023", absPathList);
        DBWrite dbwrite = new DBWrite(flightPath, deliveries, machineName, portDB);
    }
}

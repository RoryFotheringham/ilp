package uk.ac.ed.inf;

import java.util.ArrayList;

/**
 * class writes flightpath information to the database and creates the geojson output file
 */
public class Write {
    public Write(FlightPath flightPath, ArrayList<Node> absPathList, Deliveries deliveries, String date_std, String machineName, String portDB){
        Visualise vis = new Visualise(flightPath.getFlightPath(), date_std, absPathList);
        DBWrite dbwrite = new DBWrite(flightPath, deliveries, machineName, portDB);
    }
}

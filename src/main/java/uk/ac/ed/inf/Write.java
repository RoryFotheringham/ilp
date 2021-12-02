package uk.ac.ed.inf;

import java.util.ArrayList;

/**
 * class writes flightpath information to the database and creates the geojson output file
 */
public class Write {
    /**
     * writes flightpath information to the database and creates the geojson output file
     * @param flightPath the flightpath
     * @param absPathList the abstracted absolute path
     * @param deliveries deliveries object storing data on all deliveries placed and completed
     * @param date_std the specified date in standard british date formate
     * @param machineName the machine name
     * @param portDB the port of the database server
     */
    public Write(FlightPath flightPath, ArrayList<Node> absPathList, Deliveries deliveries, String date_std, String machineName, String portDB){
        Visualise vis = new Visualise(flightPath.getFlightPath(), date_std, absPathList);
        DBWrite dbwrite = new DBWrite(flightPath, deliveries, machineName, portDB);
    }
}

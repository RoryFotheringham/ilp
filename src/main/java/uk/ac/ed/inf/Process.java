package uk.ac.ed.inf;

import java.util.ArrayList;

public class Process {
    private ArrayList<Node> absPathList;// the list of nodes needed to be reached by the drone in the right order such that
                                        // a straight line connecting the nodes in a sequence will not intersect the nofly zone
    private FlightPath flightPath; // the final calculated flightpath for the drone

    /**
     * High level class processes order, delivery and area information to calculate an efficient flightpath for a drone
     * @param orders object storing information on all of the orders placed on a given date
     * @param deliveries deliveries object that will be updated with the deliveries made by the drone in the order specified by the calculated flight path
     * @param area object storing the information of noflyzones and landmarks
     */
    public Process(Orders orders, Deliveries deliveries, Area area){
        Graph graph = new Graph(area, orders); // create a graph
        // process the graph into an absolute path
        PathManagement pathManagement = new PathManagement(graph, orders);
        ArrayList<Node> absPathList = new ArrayList<>();
        absPathList.addAll(pathManagement.getAbsolutePath().getPathList());
        Path absPath = new Path(absPathList, 0);
        // process the absolute path into a flight path
        FlightPath flightPath = new FlightPath(graph, pathManagement.getAbsolutePath(), deliveries);
        this.absPathList = absPath.getPathList();
        this.flightPath = flightPath;
    }

    public ArrayList<Node> getAbsPathList() {
        return absPathList;
    }

    public FlightPath getFlightPath() {
        return flightPath;
    }

}

package uk.ac.ed.inf;

import java.util.ArrayList;

public class Process {
    private ArrayList<Node> absPathList;
    private ArrayList<Move> flightPathList;

    public Process(Orders orders, Deliveries deliveries, Area area){
        Graph graph = new Graph(area, orders);
        PathManagement pathManagement = new PathManagement(graph, orders);
        ArrayList<Node> absPathList = new ArrayList<>();
        absPathList.addAll(pathManagement.getAbsolutePath().getPathList());
        Path absPath = new Path(absPathList, 0);
        FlightPath flightPath = new FlightPath(graph, pathManagement.getAbsolutePath(), deliveries);
        this.absPathList = absPath.getPathList();
        this.flightPathList = flightPath.getFlightPath();
    }

    public ArrayList<Node> getAbsPathList() {
        return absPathList;
    }

    public ArrayList<Move> getFlightPathList() {
        return flightPathList;
    }
}

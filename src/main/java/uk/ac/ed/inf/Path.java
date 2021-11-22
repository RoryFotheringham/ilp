package uk.ac.ed.inf;

import java.util.ArrayList;

public class Path {
    ArrayList<Node> pathList;
    double totalDistance;
    ArrayList<Node> stops;

    public Path(ArrayList<Node> pathList, double totalDistance){
        this.pathList = pathList;
        this.totalDistance = totalDistance;
    }

    public void addStops(ArrayList<Node> stops){
        this.stops = stops;
    }


}

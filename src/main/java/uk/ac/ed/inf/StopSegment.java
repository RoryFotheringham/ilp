package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.LinkedList;

public class StopSegment {
    ArrayList<Node> stores = new ArrayList<>();
    Node destination;

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public void addStore(Node store){
        stores.add(store);
    }

    public ArrayList<Node> getStops(){
        ArrayList<Node> stops = new ArrayList<>(stores);
        stops.add(destination);
        return stops;
    }

    public Path bestPath(Graph graph){
        ArrayList<Path> paths = possiblePaths(graph);
        double minDistance = paths.get(0).totalDistance;
        Path minDistancePath = paths.get(0);
        for(Path path: paths){
            if(path.totalDistance < minDistance){
                minDistance = path.totalDistance;
                minDistancePath = path;
            }
        }
        return minDistancePath;
    }

    private ArrayList<Path> possiblePaths(Graph graph){
        LinkedList<Node> deliverTo = new LinkedList<>();
        deliverTo.add(this.destination);
        ArrayList<Path> paths = new ArrayList<>();
        if(stores.size() == 1){
            Path path = PathFind.findPath(graph, this.stores.get(0), this.destination, this.getStops(), deliverTo);
            paths.add(path);
            return paths;
        }else{


            ArrayList<Node> stops_00 = new ArrayList<>();
            stops_00.add(this.stores.get(0));
            stops_00.add(this.stores.get(1));
            Path path_00 = PathFind.findPath(graph, this.stores.get(0), this.stores.get(1), stops_00, null);
            ArrayList<Node> stops_01 = new ArrayList<>();
            stops_01.add(this.stores.get(1));
            stops_01.add(this.destination);

            Path path_01 = PathFind.findPath(graph, this.stores.get(1), this.destination, stops_01, deliverTo);
            Path possiblePath_0 = path_00.concatPaths(graph, path_01);
            paths.add(possiblePath_0);

            ArrayList<Node> stops_10 = new ArrayList<>();
            stops_10.add(this.stores.get(1));
            stops_10.add(this.stores.get(0));
            Path path_10 = PathFind.findPath(graph, stores.get(1), stores.get(0), stops_10, null);
            ArrayList<Node> stops_11 = new ArrayList<>();
            stops_11.add(this.stores.get(0));
            stops_11.add(this.destination);
            Path path_11 = PathFind.findPath(graph, stores.get(0), destination, stops_11, deliverTo);
            Path possiblePath_1 = path_10.concatPaths(graph, path_11);

            paths.add(possiblePath_1);
        }
        return paths;
    }
}

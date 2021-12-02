package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A StopSegment represents the stops that need to be reached in a given order
 */
public class StopSegment {
    ArrayList<Node> stores = new ArrayList<>();
    Node destination;
    String orderNo;

    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public ArrayList<Node> getStops(){
        ArrayList<Node> stops = new ArrayList<>(stores);
        stops.add(destination);
        return stops;
    }

    /**
     * selects the path from possiblePaths that takes the shortest distance
     * @param graph a Graph
     * @return minimum distance path that traverses the required nodes
     */
    public Path bestPath(Graph graph){
        ArrayList<Path> paths = possiblePaths(graph); // returns an array of length either 1 or 2 Paths that traverse the required nodes
        double minDistance = paths.get(0).totalDistance;
        Path minDistancePath = paths.get(0);
        for(Path path: paths){ // find the path that takes the shortest distance
            if(path.totalDistance < minDistance){
                minDistance = path.totalDistance;
                minDistancePath = path;
            }
        }
        return minDistancePath;
    }

    /**
     * Finds the possible paths that traverse every required node in a StopSegment.
     * If a StopSegment contains only one store then there is only one possible path (store - destination)
     * If there are two stores then there are two ways (store_1 - store_2 - customer) or (store_2 - store_1 - customer)
     * @param graph a Graph
     * @return an ArrayList(Path) of size either 1 or 2
     */
    private ArrayList<Path> possiblePaths(Graph graph){
        LinkedList<Node> deliverTo = new LinkedList<>();
        deliverTo.add(this.destination);
        LinkedList<String> orderNoList = new LinkedList<>();
        orderNoList.add(this.orderNo);
        ArrayList<Path> paths = new ArrayList<>();
        if(stores.size() == 1){ // case where there is only one store
            Path path = PathFind.findPath(graph, this.stores.get(0), this.destination, this.getStops(), deliverTo, orderNoList);
            paths.add(path);
            return paths;
        }else{
            //find a path that bridges the first two nodes according to the first combination
            ArrayList<Node> stops_00 = new ArrayList<>();
            stops_00.add(this.stores.get(0));
            stops_00.add(this.stores.get(1));
            Path path_00 = PathFind.findPath(graph, this.stores.get(0), this.stores.get(1), stops_00, null, null);
            ArrayList<Node> stops_01 = new ArrayList<>();
            stops_01.add(this.stores.get(1));
            stops_01.add(this.destination);
            //find a path that bridges the second and third nodes according to the first combination
            Path path_01 = PathFind.findPath(graph, this.stores.get(1), this.destination, stops_01, deliverTo, orderNoList);
            Path possiblePath_0 = path_00.concatPaths(graph, path_01); //concatenate these two paths
            paths.add(possiblePath_0);
            //repeat the process but according to the second combination
            ArrayList<Node> stops_10 = new ArrayList<>();
            stops_10.add(this.stores.get(1));
            stops_10.add(this.stores.get(0));
            Path path_10 = PathFind.findPath(graph, stores.get(1), stores.get(0), stops_10, null, null);
            ArrayList<Node> stops_11 = new ArrayList<>();
            stops_11.add(this.stores.get(0));
            stops_11.add(this.destination);

            Path path_11 = PathFind.findPath(graph, stores.get(0), destination, stops_11, deliverTo, orderNoList);
            Path possiblePath_1 = path_10.concatPaths(graph, path_11);

            paths.add(possiblePath_1);
        }
        return paths;
    }
}

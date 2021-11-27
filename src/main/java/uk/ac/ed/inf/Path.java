package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.LinkedList;

public class Path {
    ArrayList<Node> pathList;
    double totalDistance;
    ArrayList<Node> stops;
    LinkedList<Node> destinations;
    LinkedList<Node> stores;


    public Path(ArrayList<Node> pathList, double totalDistance) {
        this.pathList = pathList;
        this.totalDistance = totalDistance;
    }

    public void setStops(ArrayList<Node> stops) {
        this.stops = stops;
    }

    public void setDestinations(LinkedList<Node> destination) {
        this.destinations = destination;
    }

    public void setStores(LinkedList<Node> stores) {
        this.stores = stores;
    }

    private void dropHeadTail(){
        //WARNING this will not change the totalDistance accordingly
        //only use for concatenation
        if(this.pathList.size() > 1) {
            this.pathList.remove(this.pathList.size() - 1);
            this.pathList.remove(0);

        }
    }

    public Path concatPaths(Graph graph, Path path){
        if(this.stops == null || path.stops == null){
            throw new IllegalArgumentException("Path has null stops");
    }
        ArrayList<Node> newPathList = new ArrayList<>();
        ArrayList<Node> newStops = new ArrayList<>();

        Path bridgePath = PathFind.findPath(graph, this.pathList.get(this.pathList.size() - 1), path.pathList.get(0), null, null, null);
        double newTotalDistance = this.totalDistance + bridgePath.totalDistance + path.totalDistance;
        newPathList.addAll(this.pathList);

        if(bridgePath.pathList.size() > 1){
            bridgePath.dropHeadTail();
            newPathList.addAll(bridgePath.pathList);
        }else{
            path.pathList.remove(0);
        }
        newPathList.addAll(path.pathList);
        Path newPath = new Path(newPathList, newTotalDistance);

        if(this.stops.get(this.stops.size() - 1).equals(path.stops.get(0))) {
            path.stops.remove(0);
        }
        newStops.addAll(this.stops);
        newStops.addAll(path.stops);

        newPath.setStops(newStops);

        return newPath;
    }
}
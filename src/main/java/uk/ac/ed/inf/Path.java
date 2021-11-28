package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.LinkedList;

public class Path {
    private ArrayList<Node> pathList;
    double totalDistance;
    ArrayList<Node> stops;
    LinkedList<Node> destinations = new LinkedList<>();

    public Path(ArrayList<Node> pathList, double totalDistance) {
        this.pathList = pathList;
        this.totalDistance = totalDistance;
    }

    public ArrayList<Node> getPathList() {
        return pathList;
    }

    public LinkedList<Node> getDestinations() {
        return destinations;
    }

    public void setStops(ArrayList<Node> stops) {
        this.stops = stops;
    }

    public void setDestinations(LinkedList<Node> destinations) {
        this.destinations = destinations;
    }

    public void addDestination(Node destination){
        this.destinations.add(destination);
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
        LinkedList<Node> newDestinations = new LinkedList<>();

        Path bridgePath = PathFind.findPath(graph, this.pathList.get(this.pathList.size() - 1), path.pathList.get(0), null, null);
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

        if(this.destinations != null){
            newDestinations.addAll(this.destinations);
        }
        if(path.destinations != null){
            newDestinations.addAll(path.destinations);
        }

        newPath.setDestinations(newDestinations);

        return newPath;
    }
    private static double approxTotalDistanceFromPathList(ArrayList<Node> pathList){
        double approxTotalDistance = 0;
        for(int i = 0; i < pathList.size() - 1; i++){
            approxTotalDistance += pathList.get(i).distanceTo(pathList.get(i + 1));
        }
        return approxTotalDistance;
    }

    /**
     * Method pops the nodes in pathList up to the next destination node and returns in a path
     * upon reaching the destination node, removes it from the destinations but keeps it in the pathList.
     * the final node is not removed from the pathList so that the next node will 'start where it finished'
     * @return Path with consecutive nodes up to the next destination node from the path it was called on
     */
    public Path popSubPath(){
        if(this.pathList.isEmpty()){
            throw new IllegalStateException("Cannot pop subPath with empty pathList");
        }
        if(this.pathList.size() < 2){
            throw new IllegalStateException("popSubPath can only be called on a Path with a pathList >= 2");
        }
        ArrayList<Node> newPathList = new ArrayList<>();
        ArrayList<Node> newStops = new ArrayList<>();
        LinkedList<Node> newDestinations = new LinkedList<>();
        boolean endOfPath = false;
        while(!endOfPath){
            Node nextNode = this.pathList.get(0);
            this.pathList.remove(0);//pops pathList node
            newPathList.add(nextNode);
            if(this.stops.get(0) == nextNode) {
                Node nextStop = this.stops.get(0);//pops stop node
                this.stops.remove(0);
                newStops.add(nextStop);
                if(nextStop == this.destinations.getFirst()){
                    this.pathList.add(0, nextNode);
                    newDestinations.add(this.destinations.pop());
                    endOfPath = true;
                }
            }
        }
        double approxTotalDistance = approxTotalDistanceFromPathList(newPathList);
        Path subPath = new Path(newPathList, approxTotalDistance);
        subPath.setStops(newStops);
        subPath.setDestinations(newDestinations);
        return subPath;
    }
}

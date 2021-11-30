package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A path represents a a journey through a sequence of nodes, storing the nodes that must be stopped at and the orderNumbers.
 * - unlike a StopSegment, there must be an edge between two consecutive nodes in a Path
 * - A path is distinct from a FlightPath which stores a list of individual moves.
 */
public class Path {
    private ArrayList<Node> pathList;
    double totalDistance;
    private ArrayList<Node> stops;
    private LinkedList<Node> destinations = new LinkedList<>();
    LinkedList<String> orderNos = new LinkedList<>();

    public Path(ArrayList<Node> pathList, double totalDistance) {
        this.pathList = pathList;
        this.totalDistance = totalDistance;
    }

    public LinkedList<String> getOrderNos() {
        return orderNos;
    }

    public String popOrderNos(){
        return this.orderNos.pop();
    }

    public String peekOrderNos(){
        return orderNos.getFirst();
    }

    public void setOrderNos(LinkedList<String> orderNos){
        this.orderNos = orderNos;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public ArrayList<Node> getPathList() {
        return pathList;
    }

    public LinkedList<Node> getDestinations() {
        return destinations;
    }

    public Node peekPathList(){
        return this.pathList.get(0);
    }

    public Node popPathList(){
        Node node = this.pathList.get(0);
        this.pathList.remove(0);
        return node;
    }

    public void setStops(ArrayList<Node> stops) {
        this.stops = stops;
    }

    public ArrayList<Node> getStops() {
        return stops;
    }

    public void setDestinations(LinkedList<Node> destinations) {
        this.destinations = destinations;
    }

    /**
     * drops the head and tail of a path's pathList
     */
    private void dropHeadTail(){
        //WARNING this will not change the totalDistance accordingly
        //only use for concatenation
        if(this.pathList.size() > 1) {
            this.pathList.remove(this.pathList.size() - 1);
            this.pathList.remove(0);

        }
    }

    /**
     * method concatenates this path with another given path.
     * the 'bridge' between the two paths is found with PathFind.findPath which finds the shortest path from the first to the second path,
     * ensuring that there is an edge between every node in the final pathList.
     *
     * The method also concatenates all other properties in a Path object - destination, orderNo, stops.
     * @param graph a Graph
     * @param path the path which will be appended to this path
     * @return a new Path object which goes from the first node of this path to the last node of the given path
     */
    public Path concatPaths(Graph graph, Path path){
        if(this.stops == null || path.stops == null){
            throw new IllegalArgumentException("Path has null stops");
    }
        ArrayList<Node> newPathList = new ArrayList<>();
        ArrayList<Node> newStops = new ArrayList<>();
        LinkedList<Node> newDestinations = new LinkedList<>();
        LinkedList<String> newOrderNos = new LinkedList<>();
        //find a bridge between the two paths
        Path bridgePath = PathFind.findPath(graph, this.pathList.get(this.pathList.size() - 1), path.pathList.get(0), null, null, null);
        double newTotalDistance = this.totalDistance + bridgePath.totalDistance + path.totalDistance;
        newPathList.addAll(this.pathList);

        if(bridgePath.pathList.size() > 1){
            bridgePath.dropHeadTail(); //drop the head and tail to avoid the start and end of paths being duplicated
            newPathList.addAll(bridgePath.pathList);
        }else{
            path.pathList.remove(0); //remove the first node in the given path's pathList if the bridge path consists of a single node
        }
        newPathList.addAll(path.pathList);
        Path newPath = new Path(newPathList, newTotalDistance);

        if(this.stops.get(this.stops.size() - 1).equals(path.stops.get(0))) {
            path.stops.remove(0); //handles the case where the second path starts where the second path finishes
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

        if(this.orderNos != null){
            newOrderNos.addAll(this.orderNos);
        }

        if(path.orderNos != null){
            newOrderNos.addAll(path.orderNos); //add destinations and stops of the two paths
        }

        newPath.setOrderNos(newOrderNos);
        newPath.setDestinations(newDestinations);

        return newPath;
    }

    /**
     * method finds the euclidean distance between each node in a sequence.
     * distance is only approximate because the number of moves that the drone will make will be slightly larger than the euclidean distance
     * @param pathList a list of nodes in a path
     * @return a double representing the approximate distance
     */
    private static double approxTotalDistanceFromPathList(ArrayList<Node> pathList){
        double approxTotalDistance = 0;
        for(int i = 0; i < pathList.size() - 1; i++){ //finds the total distance between each node in the sequence
            approxTotalDistance += pathList.get(i).distanceTo(pathList.get(i + 1));
        }
        return approxTotalDistance;
    }

    /**
     * Method pops the nodes in pathList up to the next destination node and returns in a path.
     * Upon reaching the destination node it removes it from the destinations but keeps it in the pathList.
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
        if(this.destinations.size() < 1){
            throw new IllegalStateException("popSubPath can only be called on a path with a destination");
        }
        if(this.orderNos.size() < 1){
            throw new IllegalStateException("popSubPath can only be called on a path with at least one order number");
        }
        ArrayList<Node> newPathList = new ArrayList<>();
        ArrayList<Node> newStops = new ArrayList<>();
        LinkedList<Node> newDestinations = new LinkedList<>();
        LinkedList<String> newOrderNos = new LinkedList<>();
        boolean endOfPath = false;
        while(!endOfPath){
            Node nextNode = this.pathList.get(0);
            this.pathList.remove(0);//pops pathList node
            newPathList.add(nextNode);
            if(this.stops.get(0).equals(nextNode)) {
                Node nextStop = this.stops.get(0);//pops stop node
                this.stops.remove(0);
                newStops.add(nextStop);
                if(nextStop.equals(this.destinations.getFirst())){ //if the next node is a destination then add it to the pathList again
                    this.pathList.add(0, nextNode);
                    newDestinations.add(this.destinations.pop());
                    newOrderNos.add(this.popOrderNos());
                    endOfPath = true;
                }
            }
        }
        double approxTotalDistance = approxTotalDistanceFromPathList(newPathList);
        Path subPath = new Path(newPathList, approxTotalDistance);
        subPath.setStops(newStops);
        subPath.setDestinations(newDestinations);
        subPath.setOrderNos(newOrderNos);
        return subPath;
    }
}

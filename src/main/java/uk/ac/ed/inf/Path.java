package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.LinkedList;

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
    public String popOrderNos(){
        return this.orderNos.pop();
    }
    public LinkedList<String> getOrderNos(){
        return this.orderNos;
    }

    public String peekOrderNos(){
        return orderNos.getFirst();
    }

    public void setOrderNos(LinkedList<String> orderNos){
        this.orderNos = orderNos;
    }

    public void addOrderNos(String orderNo){
        this.orderNos.add(orderNo);
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

    public Node peekStops(){
        return this.stops.get(0);
    }

    public Node popStops(){
        Node node = this.stops.get(0);
        this.stops.remove(0);
        return node;
    }

    public Node peekPathList(){
        return this.pathList.get(0);
    }

    public Node popPathList(){
        Node node = this.pathList.get(0);
        this.pathList.remove(0);
        return node;
    }

    public void pushPathList(Node node){
        this.pathList.add(0, node);
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
        LinkedList<String> newOrderNos = new LinkedList<>();

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
            newOrderNos.addAll(path.orderNos);
        }

        newPath.setOrderNos(newOrderNos);
        newPath.setDestinations(newDestinations);

        if(!NoFlyPolice.validPathList(newPath.getPathList())){
            System.out.println("BADDD");
        }

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
                if(nextStop.equals(this.destinations.getFirst())){
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

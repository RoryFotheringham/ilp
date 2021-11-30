package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.LinkedList;

public class FlightPath {
    private static final int ANGLE_LIMIT = 10;
    private static final int DEGREES = 360;
    private static final int BATTERY_INITIAL = 1500;
    private static final double FEASIBLE_BUFFER = 1.2;
    private static final LongLat APPLETON_TOWER_LONGLAT = new LongLat(-3.186874, 55.944494);
    private static final String RETURN_ORDER_NO = "hometime"; // the order number ascribed to the journey from the last order to appleton tower.
    private static final double MOVE_DISTANCE = 0.00015;
    private static final int HOVER_VALUE = -999;

    private Path absolutePath;
    private int battery = BATTERY_INITIAL;
    private ArrayList<Move> flightPath = new ArrayList<>();


    /**
     * constructor generates a flightPath with respect to an absolute path, passed as an argument
     * @param graph a Graph
     * @param absolutePath an Absolute path for the entire day of orders
     * @param deliveries a collection of delivery data
     */
    public FlightPath(Graph graph, Path absolutePath, Deliveries deliveries){
        this.absolutePath = absolutePath;
        generateFlightPath(graph, deliveries);
    }

    public ArrayList<Move> getFlightPath() {
        return flightPath;
    }

    /**
     * checks that the last move made by a subFlightPath is 'hover'
     * @param subFlightPath a subFlightPath
     * @return true if the subFlightPath is hovering
     */
    private static boolean isHovering(ArrayList<Move> subFlightPath){
        return subFlightPath.get(subFlightPath.size() - 1).getAngle() == HOVER_VALUE;
    }

    /**
     * checks whether completing a new order then flying back to Appleton tower can be completed given the battery level
     * @param subPath a sub path representing the next possible order
     * @param homePath the path to Appleton Tower
     * @return true if the journey can be made given the battery level
     */
    private boolean isFeasiblePath(Path subPath, Path homePath){
        double nextOrderThenHome = subPath.getTotalDistance() + homePath.getTotalDistance();
        double cost = nextOrderThenHome/MOVE_DISTANCE; // finds the approximate amount of moves required to make the journey
        return (cost * FEASIBLE_BUFFER < this.battery); // seeing as the amount of moves will always be slightly larger than `cost`, FEASIBLE_BUFFER amplifies the cost by some small factor
    }

    /**
     * generates the path from the currentNode to Appleton Tower
     * @param graph a Graph
     * @param currentNode the start node
     * @return a path from current node to Appleton Tower with destination and stops set as Appleton tower,
     * The order number for a home path is set to the value of RETURN_ORDER_NO
     */
    private Path generateHomePath(Graph graph, Node currentNode){
        Node appletonNode = graph.graphMapQuery(APPLETON_TOWER_LONGLAT);
        ArrayList<Node> stops = new ArrayList<>();
        stops.add(appletonNode);
        LinkedList<Node> destinations = new LinkedList<>();
        destinations.add(appletonNode);
        LinkedList<String> returnOrderNoList = new LinkedList<>();
        returnOrderNoList.add(RETURN_ORDER_NO);
        Path homePath = PathFind.findPath(graph, currentNode, appletonNode, stops, destinations, returnOrderNoList);
        return homePath;
    }

    /**
     * generates the flightPath for the entire absolute path by popping each order as a subPath,
     * checking that it is feasible, converting it to a subFlightPath and adding it to this.flightPath
     * @param graph a Graph
     * @param deliveries a Collection of deliveries to be made
     */
    private void generateFlightPath(Graph graph, Deliveries deliveries){
        Node currentNode = absolutePath.peekPathList();
        while(absolutePath.getPathList().size() > 1){
            Path subPath = absolutePath.popSubPath(); // gets a subPath containing information for the next order
            Path homePath = generateHomePath(graph, absolutePath.peekPathList());
            if(isFeasiblePath(subPath, homePath)){
                this.flightPath.addAll(generateSubFlightPath(subPath)); // if it is feasible, generate a subFlightPath and add it to this.flightPath
                if(absolutePath.getPathList().size() > 1 && deliveries != null) {
                    deliveries.makeDelivery(subPath.peekOrderNos()); // adds the delivery corresponding to the orderNo to madeDeliveries
                    currentNode = subPath.getDestinations().getFirst(); // since the order has been completed our current position is the destination of the previous subPath
                }
            }
            if(this.battery < 0){
                throw new IllegalStateException("battery cannot be below 0");
            }
        }
        if(!(currentNode.getLongLat().closeTo(APPLETON_TOWER_LONGLAT))){ // if, after finishing all feasible orders, we are not yet at Appleton...
            Path homePath = generateHomePath(graph, currentNode);
            this.flightPath.addAll(generateSubFlightPath(homePath)); // get home from the current node
        }
    }

    private ArrayList<Move> generateSubFlightPath(Path subPath) {
        if (subPath.getDestinations().size() != 1) {
            throw new IllegalArgumentException("subPath must have only one destination");
        }
        if (subPath.getPathList().size() < 2) {
            throw new IllegalArgumentException("Cannot generate subFlightPath from a pathList with a size < 2");
        }

        ArrayList<Move> subFlightPath = new ArrayList<>();
        LinkedList<Node> pathList = new LinkedList<>(subPath.getPathList());
        LinkedList<Node> stops = new LinkedList<>(subPath.getStops());
        String orderNo = subPath.getOrderNos().getFirst();
        Node currentPosNode = pathList.pop();
        LongLat currentPos = currentPosNode.getLongLat();
        Node destinationNode = pathList.pop();
        LongLat destinationPos = destinationNode.getLongLat();

        while(!stops.isEmpty()){
            LongLat nextPos = findBestAngle(currentPos, destinationPos, subFlightPath, orderNo);
            if (isHovering(subFlightPath)){
                if(destinationNode.equals(stops.getFirst())){
                    stops.removeFirst();
                }
                if(!pathList.isEmpty()) {
                    destinationNode = pathList.pop();
                    destinationPos = destinationNode.getLongLat();
                }
            }
            currentPos = nextPos;
        }
        return subFlightPath;
    }

    private LongLat findBestAngle(LongLat currentPos, LongLat destination, ArrayList<Move> subFlightPath, String orderNo){

        if(currentPos.closeTo(destination)){
            Move newMove = new Move(orderNo, currentPos.getLongitude(), currentPos.getLatitude(), HOVER_VALUE, currentPos.getLongitude(), currentPos.getLatitude());
            subFlightPath.add(newMove);
            this.battery -= 1;
            return currentPos;
        }
        int minDistanceAngle = 0;
        LongLat minDistancePos = currentPos.nextPosition(minDistanceAngle);
        double minDistance = minDistancePos.distanceTo(destination);
        for(int candidateAngle = 0; candidateAngle < DEGREES; candidateAngle += ANGLE_LIMIT){
            LongLat candidatePos = currentPos.nextPosition(candidateAngle);
            double candidateDistance = candidatePos.distanceTo(destination);
            if(candidateDistance < minDistance) {
                minDistance = candidateDistance;
                minDistancePos = candidatePos;
                minDistanceAngle = candidateAngle;
            }
        }
        Move newMove = new Move(orderNo, currentPos.getLongitude(), currentPos.getLatitude(), minDistanceAngle, minDistancePos.getLongitude(), minDistancePos.getLatitude());

        subFlightPath.add(newMove);
        this.battery -= 1;
        return minDistancePos;
    }


}

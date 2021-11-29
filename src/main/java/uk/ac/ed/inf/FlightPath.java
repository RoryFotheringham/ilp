package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.LinkedList;

public class FlightPath {
    private static final int ANGLE_LIMIT = 10;
    private static final int DEGREES = 360;
    private static final int BATTERY_INITIAL = 100;
    private static final double FEASIBLE_BUFFER = 1.2;
    private static final LongLat APPLETON_TOWER_LONGLAT = new LongLat(-3.186874, 55.944494);
    private static final String RETURN_ORDER_NO = "hometime";
    private static final double MOVE_DISTANCE = 0.00015;
    private static final int HOVER_VALUE = -999;

    Path absolutePath;
    int battery = BATTERY_INITIAL;
    ArrayList<Move> flightPath = new ArrayList<>();
    


    public FlightPath(Graph graph, Path absolutePath){
        this.absolutePath = absolutePath;
        generateFlightPath(graph);
    }

    public static boolean isHovering(ArrayList<Move> subFlightPath){
        return subFlightPath.get(subFlightPath.size() - 1).getAngle() == HOVER_VALUE;
    }

    public boolean isFeasiblePath(Path subPath, Path homePath){
        double nextOrderThenHome = subPath.getTotalDistance() + homePath.getTotalDistance();
        double cost = nextOrderThenHome/MOVE_DISTANCE;
        return (cost * FEASIBLE_BUFFER < this.battery);
    }

    public Path generateHomePath(Graph graph, Node currentNode){
        Node appletonNode = graph.graphMapQuery(APPLETON_TOWER_LONGLAT);
        ArrayList<Node> stops = new ArrayList<>();
        stops.add(currentNode);
        stops.add(appletonNode);
        LinkedList<Node> destinations = new LinkedList<>();
        destinations.add(appletonNode);
        LinkedList<String> returnOrderNoList = new LinkedList<>();
        returnOrderNoList.add(RETURN_ORDER_NO);
        Path homePath = PathFind.findPath(graph, currentNode, appletonNode, stops, destinations, returnOrderNoList);
        return homePath;
    }

    public void generateFlightPath(Graph graph){
        Node currentNode = absolutePath.peekPathList();
        while(absolutePath.getPathList().size() > 1){
            if(this.battery == 0){
                throw new IllegalStateException("battery cannot be below 0");
            }
            Path subPath = absolutePath.popSubPath();
            Path homePath = generateHomePath(graph, absolutePath.peekPathList());
            if(isFeasiblePath(subPath, homePath)){
                this.flightPath.addAll(generateSubFlightPath(subPath));
                if(absolutePath.getPathList().size() > 1) {
                    currentNode = subPath.getDestinations().getFirst();
                }
            }
        }
        if(!(currentNode.getLongLat().closeTo(APPLETON_TOWER_LONGLAT))){
            Path homePath = generateHomePath(graph, currentNode);
            this.flightPath.addAll(generateSubFlightPath(homePath));
        }
    }

    public ArrayList<Move> generateSubFlightPath(Path subPath) {
        if (subPath.destinations.size() != 1) {
            throw new IllegalArgumentException("subPath must have only one destination");
        }
        if (subPath.getPathList().size() < 2) {
            throw new IllegalArgumentException("Cannot generate subFlightPath from a pathList with a size < 2");
        }
        ArrayList<Move> subFlightPath = new ArrayList<>();
        LinkedList<Node> pathList = new LinkedList<>(subPath.getPathList());
        LinkedList<Node> stops = new LinkedList<>(subPath.stops);
        String orderNo = subPath.orderNos.getFirst();
        LongLat currentPos = pathList.pop().getLongLat();
        LongLat destinationPos = pathList.pop().getLongLat();
        stops.removeFirst();
        while(!stops.isEmpty()){
            LongLat nextPos = findBestAngle(currentPos, destinationPos, subFlightPath, orderNo);
            if (isHovering(subFlightPath)){
                if(!pathList.isEmpty()) {
                    destinationPos = pathList.pop().getLongLat();
                }
                stops.removeFirst();
            }
            currentPos = nextPos;
        }
        return subFlightPath;
    }

    public LongLat findBestAngle(LongLat currentPos, LongLat destination, ArrayList<Move> subFlightPath, String orderNo){

        if(currentPos.closeTo(destination)){
            Move newMove = new Move(orderNo, currentPos.longitude, currentPos.latitude, HOVER_VALUE, currentPos.longitude, currentPos.latitude);
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
        Move newMove = new Move(orderNo, currentPos.longitude, currentPos.latitude, minDistanceAngle, minDistancePos.longitude, minDistancePos.latitude);
        subFlightPath.add(newMove);
        this.battery -= 1;
        return minDistancePos;
    }


}

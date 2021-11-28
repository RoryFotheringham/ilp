package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.LinkedList;

public class FlightPath {
    private static final int ANGLE_LIMIT = 10;
    private static final int DEGREES = 360;
    private static final int BATTERY_INITIAL = 1500;

    ArrayList<Integer> flightPath = new ArrayList<>();
    Path absolutePath;
    int battery = BATTERY_INITIAL;
    


    public FlightPath(Path absolutePath){
        this.absolutePath = absolutePath;
        generateFlightPath();
    }

    public void generateFlightPath(){
        
    }
    
    public ArrayList<Integer> generateSubFlightPath(Path subPath){
        if(subPath.destinations.size() != 1){
            throw new IllegalArgumentException("subPath must have only one destination");
        }
        if(subPath.getPathList().size() < 2){
            throw new IllegalArgumentException("Cannot generate subFlightPath from a pathList with a size < 2");
        }
        ArrayList<Integer> subFlightPath = new ArrayList<>();
        LinkedList<Node> pathList = new LinkedList<>(subPath.getPathList());
        LinkedList<Node> stops = new LinkedList<>(subPath.stops);
        LongLat currentPos = pathList.pop().getLongLat();
        LongLat destinationPos = pathList.pop().getLongLat();
        stops.removeFirst();
        while(!stops.isEmpty()){
            LongLat nextPos = findBestAngle(currentPos, destinationPos, subFlightPath);
            if (subFlightPath.get(subFlightPath.size() - 1) == -999){
                if(!pathList.isEmpty()) {
                    destinationPos = pathList.pop().getLongLat();
                }
                    stops.removeFirst();

            }
            currentPos = nextPos;
        }
        return subFlightPath;
    }

    public LongLat findBestAngle(LongLat currentPos, LongLat destination, ArrayList<Integer> subFlightPath){
        if(currentPos.closeTo(destination)){
            subFlightPath.add(-999);
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
        subFlightPath.add(minDistanceAngle);
        return minDistancePos;
    }
}

package uk.ac.ed.inf;

import java.util.LinkedList;

public class FlightPath {
    private static final int ANGLE_LIMIT = 10;
    private static final int DEGREES = 360;

    int[] flightPath = new int[1500];
    LinkedList<Path> absolutePath;
    int

    public FlightPath(LinkedList<Path> absolutePath){
        this.absolutePath = absolutePath;
        generateFlightPath();
    }

    public void generateFlightPath(){


    }

    public LongLat findBestAngle(LongLat currentPos, LongLat destination, int flightPathIndex, int[] path){
        int minDistanceAngle = 0;
        LongLat minDistancePos = currentPos.nextPosition(minDistanceAngle);
        double minDistance = minDistancePos.distanceTo(destination);
        for(int candidateAngle = 0; candidateAngle < DEGREES/ANGLE_LIMIT; candidateAngle += ANGLE_LIMIT){
            LongLat candidatePos = currentPos.nextPosition(candidateAngle);
            if(candidatePos.closeTo(destination)){
                path[flightPathIndex] = -999;
                return candidatePos;
            }else {
                double candidateDistance = candidatePos.distanceTo(destination);
                if(candidateDistance < minDistance) {
                     minDistance = candidateDistance;
                     minDistancePos = candidatePos;
                     minDistanceAngle = candidateAngle;
                }
            }
        }
        path[flightPathIndex] = minDistanceAngle;
        return minDistancePos;
    }
}

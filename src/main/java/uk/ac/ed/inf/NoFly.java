package uk.ac.ed.inf;

import java.util.ArrayList;

/**
 * Class stores the points of a single polygon that represents a NoFly zone in the map
 */
public class NoFly {
    ArrayList<LongLat> noFly;

    /**
     * basic constructor
     * @param noFly list of LongLat coords
     */
    public NoFly(ArrayList<LongLat> noFly){
        this.noFly = noFly;
    }

    /**
     * checks if a straight line between two points intersects
     * the polygon described by a NoFly object.
     * @param p point p
     * @param q point q
     * @return returns true if the line intersects
     */
    public boolean isIntersecting(LongLat p, LongLat q){
        //todo write the actual hing
        return false;
    }
}

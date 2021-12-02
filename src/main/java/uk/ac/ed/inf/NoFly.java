package uk.ac.ed.inf;

import java.awt.geom.*;

import java.util.ArrayList;

/**
 * Class stores the points of a single polygon that represents a NoFly zone in the map
 */
public class NoFly {
    ArrayList<Line2D> noFly = new ArrayList<>();

    /**
     * creates a NoFly object from a list of points that describe the polygon outlining the noflyzone
     * @param points the vertices of the polygon that describes the noflyzone
     */
    public NoFly(ArrayList<Point2D> points){
        concatenatePoints(points);
    }

    /**
     * creates a line between each consecutive pair of points in the noFlyzone
     * @param points the vertices of the polygon that describes the noflyzone
     */
    private void concatenatePoints(ArrayList<Point2D> points){
        int len = points.size();
        for(int i = 0; i < len; i++){
            Point2D p = points.get(i);
            Point2D q = points.get((i + 1) % len);
            this.noFly.add(new Line2D.Double(p, q));
        }
    }

    /**
     * checks if a straight line between two points intersects
     * the polygon described by a NoFly object.
     * @param line the line in question
     * @return true if the line intersects
     */
    public boolean isIntersecting(Line2D line){
        for(Line2D segment: noFly){
            if(segment.intersectsLine(line)){
                return true;
            }
        }
        return false;
    }
}

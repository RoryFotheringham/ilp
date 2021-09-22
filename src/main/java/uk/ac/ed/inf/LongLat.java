package uk.ac.ed.inf;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;

public class LongLat {
    public static final double CONFINEMENT_AREA_X1 = -3.192473; //constants outlining the confinement area in long-lat degrees
    public static final double CONFINEMENT_AREA_X2 = -3.184319;
    public static final double CONFINEMENT_AREA_Y1 = 55.942617;
    public static final double CONFINEMENT_AREA_Y2 = 55.946333;
    double longitude;
    double latitude;

    public LongLat(double longitude, double latitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * method which finds the Euclidean distance between two longLat objects
     * @param longLat a longLat object
     * @return returns the Euclidean distance between two longLat objects
     */
    public double distanceTo(LongLat longLat){
        double x1 = this.longitude;
        double y1 = this.latitude;
        double x2 = longLat.longitude;
        double y2 = longLat.latitude;
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)); //Calculates the Euclidean distance
    }

    /**
     * method that returns whether two longLat objects are within 0.00015 degrees of one another
     * @param longLat a LongLat object
     * @return returns true if the two points are within 0.00015 degrees of one another.
     */
    public boolean closeTo(LongLat longLat){
        boolean close = false;
        if(distanceTo(longLat) <= 0.00015){
            close = true;
        }
        return close;
    }

    /**
     * method that checks if the longitude and latitude points are strictly within
     * the confinement area.
     * @return returns true if longLat points are within the confinement area
     */
    public boolean isConfined(){
        boolean confined = true;
        if (this.longitude <= CONFINEMENT_AREA_X1
                || this.longitude >= CONFINEMENT_AREA_X2
                || this.latitude <= CONFINEMENT_AREA_Y1
                || this.latitude >= CONFINEMENT_AREA_Y2){
            confined = false;
        }
        return confined;
    }

    //TODO nextPosition class.

}

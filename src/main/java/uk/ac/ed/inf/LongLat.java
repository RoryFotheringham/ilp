package uk.ac.ed.inf;

//import com.sun.org.apache.xpath.internal.functions.FuncFalse;

public class LongLat {
    public static final double CONFINEMENT_AREA_X1 = -3.192473; //constants outlining the confinement area in long-lat degrees
    public static final double CONFINEMENT_AREA_X2 = -3.184319;
    public static final double CONFINEMENT_AREA_Y1 = 55.942617;
    public static final double CONFINEMENT_AREA_Y2 = 55.946333;
    public static final double MOVEMENT_DISTANCE =  0.00015;
    public static final int HOVER_VALUE = -999;
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

    /**
     * method that calculates the effect of moving in a straight line pointing towards a given angle.
     * It only accepts angles that are a multiple of 10 or the value -999
     * which is a junk value intepreted as 'hover' and does not change LongLat coords.
     * @param angle the angle that the drone moves in
     * @return returns a LongLat object with updated coordinates
     */
    public LongLat nextPosition(int angle){
        if (angle % 10 == 0) {
            double deltaLong = MOVEMENT_DISTANCE * Math.cos(Math.toRadians(angle));
            double deltaLat = MOVEMENT_DISTANCE * Math.sin(Math.toRadians(angle));

            if (!isConfined()){
                System.out.println("bad"); //TODO figure out if a new exeption class is needed.
            }

            return new LongLat(this.longitude + deltaLong, this.latitude + deltaLat);
        }
        else if (angle == HOVER_VALUE){
            return new LongLat(this.longitude, this.latitude);
        }
        else throw new IllegalArgumentException("Angle must be either a multiple of 10 or " + HOVER_VALUE);
    }

//TODO error handling and input checking

}

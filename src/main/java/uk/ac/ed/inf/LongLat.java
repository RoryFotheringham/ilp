package uk.ac.ed.inf;

import com.google.gson.Gson;
import com.what3words.javawrapper.What3WordsV3;
import com.what3words.javawrapper.response.ConvertToCoordinates;
import com.what3words.javawrapper.response.Coordinates;
import com.what3words.javawrapper.response.Square;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * LongLat class is representation of a Longitude and Latitude coordinate pair.
 * stores values and performs basic calculations.
 */
public class LongLat {
    public static final double CONFINEMENT_AREA_X1 = -3.192473; //constants outlining the confinement area in long-lat degrees
    public static final double CONFINEMENT_AREA_X2 = -3.184319;
    public static final double CONFINEMENT_AREA_Y1 = 55.942617;
    public static final double CONFINEMENT_AREA_Y2 = 55.946333;
    public static final double MOVEMENT_DISTANCE =  0.00015;
    public static final int HOVER_VALUE = -999;
    double longitude;
    double latitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongLat longLat = (LongLat) o;
        return Double.compare(longLat.longitude, longitude) == 0 &&
                Double.compare(longLat.latitude, latitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }

    public LongLat(double longitude, double latitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LongLat(String what3words, String machineName, String port){
        double[] coords = WhatThreeWords.convertToCoordinates(what3words, machineName, port);
        this.longitude = coords[0];
        this.latitude = coords[1];
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
     * It only accepts angles that are a multiple of 10 or the value -999.
     * program terminates if the value is not one of the accepted values
     * @param angle the angle that the drone moves in
     * @return returns a LongLat object with updated coordinates
     */
    public LongLat nextPosition(int angle){
        if (angle % 10 == 0) {
            double deltaLong = MOVEMENT_DISTANCE * Math.cos(Math.toRadians(angle));
            double deltaLat = MOVEMENT_DISTANCE * Math.sin(Math.toRadians(angle));

            return new LongLat(this.longitude + deltaLong, this.latitude + deltaLat);
        }
        else if (angle == HOVER_VALUE){ //Returns new LongLat obj. with current long and lat coordinates if value is HOVER_VALUE
            return new LongLat(this.longitude, this.latitude);
        }
        else{ //If angle is not one of the accepted values, the system will terminate and output a diagnostic message
            System.out.println("Angle must be either a multiple of 10 or " + HOVER_VALUE);
            System.exit(1);
            return null; //This line will not be run as the program will have terminated but it is necessary for compilation
        }
    }
}

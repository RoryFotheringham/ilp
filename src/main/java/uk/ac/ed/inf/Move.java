package uk.ac.ed.inf;

/**
 * class represents a single move in the flight path.
 * information is stored in the format that will be written to the database
 */
public class Move {
    private String orderNo;
    private double fromLongitude;
    private double fromLatitude;
    private int angle;
    private double toLongitude;
    private double toLatitude;

    /**
     * Simple constructor creates a Move object which directly stores the parameters passed to it
     * @param orderNo the number of the order that the move is a part of
     * @param fromLongitude the longitude ordinate that the drone is coming from
     * @param fromLatitude the latitude ordinate that the drone is coming from
     * @param angle the angle that the drone is moving in
     * @param toLongitude the longitude ordinate that the drone is moving to
     * @param toLatitude the latitude ordinate that the drone is moving to
     */
    public Move(String orderNo, double fromLongitude, double fromLatitude, int angle, double toLongitude, double toLatitude) {
        this.orderNo = orderNo;
        this.fromLongitude = fromLongitude;
        this.fromLatitude = fromLatitude;
        this.angle = angle;
        this.toLongitude = toLongitude;
        this.toLatitude = toLatitude;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public double getFromLongitude() {
        return fromLongitude;
    }

    public double getFromLatitude() {
        return fromLatitude;
    }

    public int getAngle() {
        return angle;
    }

    public double getToLongitude() {
        return toLongitude;
    }

    public double getToLatitude() {
        return toLatitude;
    }
}

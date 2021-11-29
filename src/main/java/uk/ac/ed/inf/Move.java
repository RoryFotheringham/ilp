package uk.ac.ed.inf;

public class Move {
    private String orderNo;
    private double fromLongitude;
    private double fromLatitude;
    private int angle;
    private double toLongitude;
    private double toLatitude;

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

package uk.ac.ed.inf;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.*;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains area information, namely the noFlyList and Landmarks
 */
public class Area {
    private static final String LANDMARKS_LOCATION = "buildings/landmarks.geojson";
    private static final String NOFLY_LOCATION = "buildings/no-fly-zones.geojson";
    ArrayList<NoFly> noFlyList = new ArrayList<>();
    ArrayList<Landmark> landmarks = new ArrayList<>();
    private String portWeb;
    private String machineName;

    /**
     * basic constructor, fills reads and stores the no-fly-zone and landmarks information
     * @param machineName name of machine, server is running on
     * @param portWeb the port that the webserver is running on
     */
    public Area(String machineName, String portWeb){
        this.portWeb = portWeb;
        this.machineName = machineName;
        createNoFlyList();
        createLandmarks();
    }

    /**
     * checks if a line segment made by two longLat objects intersects any of the no fly zones
     * @param ll_1 first longlat object
     * @param ll_2 second longlat object
     * @return true if the line segment intersects
     */
    public boolean intersectsNoFly(LongLat ll_1, LongLat ll_2){
        Point2D p = new Point2D.Double(ll_1.getLongitude(), ll_1.getLatitude());
        Point2D q = new Point2D.Double(ll_2.getLongitude(), ll_2.getLatitude());
        Line2D line = new Line2D.Double(p, q);
        for (NoFly noFly: noFlyList){
            if (noFly.isIntersecting(line)){
                return true;
            }
        }
        return false;
    }

    /**
     * checks if a line drawn between two nodes intersects any of the no fly zones
     * @param node1 first node
     * @param node2 second node
     * @return true if they intersect
     */
    public boolean intersectsNoFly(Node node1, Node node2){
        Point2D p = new Point2D.Double(node1.getLongLat().getLongitude(), node1.getLongLat().getLatitude());
        Point2D q = new Point2D.Double(node2.getLongLat().getLongitude(), node2.getLongLat().getLatitude());
        Line2D line = new Line2D.Double(p, q);
        for (NoFly noFly: noFlyList){
            if (noFly.isIntersecting(line)){
                return true;
            }
        }
        return false;
    }

    /**
     * converts a geojson Polygon object to an ArrayList(NoFly) and stores it in the area object
     * @param featureCollection feature collection that is a polygon.
     */
    private void noFlyListFromPolygon(FeatureCollection featureCollection){
        Geometry geom;
        List list;
        ArrayList<LngLatAlt> coords;
        for(Feature feature: featureCollection){
            geom = (Geometry) feature.getGeometry();
            list = geom.getCoordinates();
            coords = (ArrayList<LngLatAlt>) list.get(0);
            ArrayList<Point2D> points = new ArrayList<>();
            for(LngLatAlt coord: coords){
                points.add(new Point2D.Double(coord.getLongitude(), coord.getLatitude()));
            }
            NoFly noFly = new NoFly(points);
            noFlyList.add(noFly);
        }
    }

    /**
     * combines helper functions to read no fly data and then add it as a noflylist
     */
    private void createNoFlyList(){
        ArrayList<NoFly> newNoFlyList = new ArrayList<NoFly>();
        FeatureCollection featureCollection = FeaturesFromBuildingsServer(NOFLY_LOCATION);
        noFlyListFromPolygon(featureCollection);
    }

    /**
     * creates landmark objects read from the server and adds them to an ArrayList(Landmark)
     */
    private void createLandmarks(){
        FeatureCollection featureCollection = FeaturesFromBuildingsServer(LANDMARKS_LOCATION);
        for(Feature feature: featureCollection){
            Point point = (Point) feature.getGeometry();
            double lng = point.getCoordinates().getLongitude();
            double lat = point.getCoordinates().getLatitude();
            this.landmarks.add(new Landmark(new LongLat(lng, lat), feature));
        }
    }

    /**
     * reads a geojson FeatureCollection from the web server from a given location
     * @param location the location of the file on the server that the geojson object is found
     * @return the feature collection object found at the location
     */
    private FeatureCollection FeaturesFromBuildingsServer(String location){
        Client client = new Client();
        String urlString = ("http://" + machineName + ":" + this.portWeb + "/" + location);
        String geojsonString = client.getResponse(urlString);

        FeatureCollection featureCollection = null;
        try {
            featureCollection =
                    new ObjectMapper().readValue(geojsonString, FeatureCollection.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Fatal error occurred whilst processing GEOJSON data received from: " + urlString);
            System.exit(1);
        }
        return featureCollection;
    }
}



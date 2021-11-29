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
    String portWeb;
    String machineName;

    public Area(String machineName, String portWeb){
        this.portWeb = portWeb;
        this.machineName = machineName;
        createNoFlyList();
        createLandmarks();
    }

    public boolean intersectsNoFly(Node node1, Node node2){
        Point2D p = new Point2D.Double(node1.getLongLat().getLongitude(), node1.getLongLat().getLatitude());
        Point2D q = new Point2D.Double(node2.getLongLat().getLongitude(), node1.getLongLat().getLatitude());
        Line2D line = new Line2D.Double(p, q);
        for (NoFly noFly: noFlyList){
            if (noFly.isIntersecting(line)){
                return true;
            }
        }
        return false;
    }


    public void noFlyListFromPolygon(FeatureCollection featureCollection){
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

    public void createNoFlyList(){
        ArrayList<NoFly> newNoFlyList = new ArrayList<NoFly>();
        FeatureCollection featureCollection = FeaturesFromBuildingsServer(NOFLY_LOCATION);
        noFlyListFromPolygon(featureCollection);
    }

    public void createLandmarks(){
        FeatureCollection featureCollection = FeaturesFromBuildingsServer(LANDMARKS_LOCATION);
        for(Feature feature: featureCollection){
            Point point = (Point) feature.getGeometry();
            double lng = point.getCoordinates().getLongitude();
            double lat = point.getCoordinates().getLatitude();
            this.landmarks.add(new Landmark(new LongLat(lng, lat), feature));
        }
    }

    public FeatureCollection FeaturesFromBuildingsServer(String location){
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



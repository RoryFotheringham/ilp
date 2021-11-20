package uk.ac.ed.inf;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Geometry;
import org.geojson.Point;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Class contains area information, namely the noFlyList and Landmarks
 */
public class Area {
    private static final String LANDMARKS_LOCATION = "buildings/landmarks.geojson";
    private static final String NOFLY_LOCATION = "buildings/no-fly-zones.geojson";
    ArrayList<NoFly> noFlyList;
    ArrayList<Landmark> landmarks;
    String portWeb;
    String machineName;



    public Area(String machineName, String portWeb){
        this.portWeb = portWeb;
        this.machineName = machineName;
        this.noFlyList = createNoFlyList();
        this.landmarks = createLandmarks();

    }

    public boolean intersectsNoFly(Node node1, Node node2){
        //todo
        return false;
    }

    public boolean inNoFlyZone(LongLat longLat){
        //todo
        return false;
    }

    public ArrayList<NoFly> createNoFlyList(){
        ArrayList<NoFly> newNoFlyList = new ArrayList<NoFly>();
        //todo read from server and convert to NoFly type which is list of LongLats



        return newNoFlyList;
    }

    public ArrayList<Landmark> createLandmarks(){
        ArrayList<Landmark> newLandmarks = new ArrayList<Landmark>();
        Client client = new Client();
        String urlString = ("http://" + machineName + ":" + this.portWeb + "/" + LANDMARKS_LOCATION);
        String landmarksGeojson = client.getResponse(urlString);

        FeatureCollection featureCollection = null;
        try {
            featureCollection =
                    new ObjectMapper().readValue(landmarksGeojson, FeatureCollection.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.exit(1);
        }

        for(Feature feature: featureCollection){
            Point point = (Point) feature.getGeometry();
            double lng = point.getCoordinates().getLongitude();
            double lat = point.getCoordinates().getLatitude();
            newLandmarks.add(new Landmark(new LongLat(lng, lat), feature));
        }

        return newLandmarks;
    }
}



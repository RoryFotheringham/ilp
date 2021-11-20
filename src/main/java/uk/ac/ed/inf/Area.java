package uk.ac.ed.inf;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.geojson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        FeatureCollection featureCollection = FeaturesFromBuildingsServer(NOFLY_LOCATION);

        Geometry geom; //todo make this even barely readable and test it
        List list;
        ArrayList<LngLatAlt> coords;
        for(Feature feature: featureCollection){
            geom = (Geometry) feature.getGeometry();
            list = geom.getCoordinates();
            coords = (ArrayList<LngLatAlt>) list.get(0);
            for(LngLatAlt coord: coords){
                noFlyList.add(new NoFly(new LongLat(coord.getLongitude(), coord.getLatitude())));
            }

        }

        return newNoFlyList;
    }


    public ArrayList<Landmark> createLandmarks(){
        ArrayList<Landmark> newLandmarks = new ArrayList<Landmark>();
        FeatureCollection featureCollection = FeaturesFromBuildingsServer(LANDMARKS_LOCATION);
        for(Feature feature: featureCollection){
            Point point = (Point) feature.getGeometry();
            double lng = point.getCoordinates().getLongitude();
            double lat = point.getCoordinates().getLatitude();
            newLandmarks.add(new Landmark(new LongLat(lng, lat), feature));
        }

        return newLandmarks;
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
            System.out.println("Fatal error ocurred whilst processing GEOJSON data received from: " + urlString);
            System.exit(1);
        }
        return featureCollection;
    }
}



package uk.ac.ed.inf;



import com.mapbox.geojson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Visualise {
    public Visualise(ArrayList<Move> flightPath, String ddMMYYYY, ArrayList<Node> pathList){
        ArrayList<Point> pointList = makePointList(flightPath);
        ArrayList<Point> nodePointList = pointsFromAbsolutePath(pathList);
        String nodeString = jsonFromPoints(nodePointList);
        String geoJsonPathString = jsonFromPoints(pointList);
        makeFile(geoJsonPathString, ddMMYYYY);
        makeFile(nodeString, "node");
    }

    private String makeFileName(String ddMMYYY){
        return "drone-" + ddMMYYY + ".geojson";
    }

    private void makeFile(String geoJsonPathString, String ddMMYYY){
        String fileName = makeFileName(ddMMYYY);
        try{
            FileWriter file = new FileWriter(fileName);
            file.write(geoJsonPathString);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while trying to create: " + fileName);
        }
    }

    private String jsonFromPoints(ArrayList pointList){
        LineString lineString = LineString.fromLngLats(pointList);
        Geometry geom = (Geometry)lineString;
        Feature feature = Feature.fromGeometry(geom);
        FeatureCollection featureCollection = FeatureCollection.fromFeature(feature);
        return featureCollection.toJson();
    }
    private ArrayList<Point> pointsFromAbsolutePath(ArrayList<Node> absolutePath){
        ArrayList<Point> pointList = new ArrayList<>();
        for(Node node : absolutePath){
            double lng = node.getLongLat().getLongitude();
            double lat = node.getLongLat().getLatitude();
            Point point = Point.fromLngLat(lng, lat);
            pointList.add(point);
        }
        return pointList;
    }

    private ArrayList<Point> makePointList(ArrayList<Move> flightPath){
        ArrayList<Point> pointList = new ArrayList<>();
        for(Move move: flightPath){
            double lng = move.getFromLongitude();
            double lat = move.getFromLatitude();
            Point point = Point.fromLngLat(lng, lat);
            pointList.add(point);
        }
        return pointList;

    }
}

package uk.ac.ed.inf;



import com.mapbox.geojson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * class handles the visualisation of flight path data
 */
public class Visualise {
    /**
     *constructor calls functions that create the geoJson files
     * @param flightPath the flightpath that will be converted to a geojson string
     * @param date_std the date in standard british format
     * @param pathList path list of nodes - used for visualising the list of nodes in the Path
     *                 - note that the functionality to create this file has been commented to avoid cluttering the submission
     *                 but has been a useful visual aid for comparing the Path with the FlightPath and may be
     *                 useful for future maintenance.
     */
    public Visualise(ArrayList<Move> flightPath, String date_std, ArrayList<Node> pathList){
        ArrayList<Point> pointList = makePointList(flightPath);
        ArrayList<Point> nodePointList = pointsFromAbsolutePath(pathList);
        String nodeString = jsonFromPoints(nodePointList);
        String geoJsonPathString = jsonFromPoints(pointList);
        makeFile(geoJsonPathString, date_std); //creates the geojson FeatureCollection file in the top level dir.
        //makeFile(nodeString, "node"); // create the pathList file
    }

    private String makeFileName(String date_std){
        return "drone-" + date_std + ".geojson";
    }

    /**
     * create a geoJson file in the top level of the project from a geoJsonString
     * @param geoJsonPathString the geoJson string
     * @param date_std the date
     */
    private void makeFile(String geoJsonPathString, String date_std){
        String fileName = makeFileName(date_std);
        try{
            FileWriter file = new FileWriter(fileName);
            file.write(geoJsonPathString);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while trying to create: " + fileName);
        }
    }

    /**
     * Processes an ArrayList(Point) into a json string
     * @param pointList
     * @return
     */
    private String jsonFromPoints(ArrayList pointList){
        LineString lineString = LineString.fromLngLats(pointList);
        Geometry geom = (Geometry)lineString;
        Feature feature = Feature.fromGeometry(geom);
        FeatureCollection featureCollection = FeatureCollection.fromFeature(feature);
        return featureCollection.toJson();
    }

    /**
     * converts an absolute path into a list of Points
     * @param absolutePath path
     * @return list of points corresponding to the nodes in absolute path
     */
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

    /**
     * processes a list of moves into a list of Points
     * @param flightPath list of moves
     * @return list of Points
     */
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

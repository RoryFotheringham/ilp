package uk.ac.ed.inf;

import org.geojson.Feature;

public class Landmark {
    LongLat longLat;
    Feature feature;

    public Landmark(LongLat longLat, Feature feature){
        this.longLat = longLat;
        this.feature = feature;
    }
}

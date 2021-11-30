package uk.ac.ed.inf;

import org.geojson.Feature;

/**
 * class stores information about a Landmark
 */
public class Landmark {
    private LongLat longLat;
    private Feature feature;

    public Landmark(LongLat longLat, Feature feature){
        this.longLat = longLat;
        this.feature = feature;
    }

    public LongLat getLongLat() {
        return longLat;
    }

    public Feature getFeature() {
        return feature;
    }
}

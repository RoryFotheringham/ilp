package uk.ac.ed.inf;

import org.geojson.Feature;

/**
 * class stores information about a Landmark
 */
public class Landmark {
    private LongLat longLat;
    private Feature feature;

    /**
     * creates a landmark object from its location and feature information
     * @param longLat location of the landmark
     * @param feature the feature information of the landmark
     */
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

package uk.ac.ed.inf;

import java.util.ArrayList;

public class StopSegment {
    ArrayList<Node> stores = new ArrayList<>();
    Node destination = null;

    public StopSegment() {
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public void addStore(Node store){
        stores.add(store);
    }

    public void sortStopSegment(Graph graph){

    }

}

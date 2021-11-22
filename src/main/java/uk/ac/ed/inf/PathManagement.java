package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class PathManagement {
    ArrayList<StopSegment> stopSegments = new ArrayList<>();

    public PathManagement(Graph graph, Orders orders){
        generateStopSegments(graph, orders);
    }

    public void generateStopSegments(Graph graph, Orders orders){
        for(OrderDetails orderDetails: orders.ordersList){
            StopSegment stopSegment = new StopSegment();
            Node destinationNode = graph.graphMap.get(orderDetails.deliverTo);
            stopSegment.setDestination(destinationNode);
            for(Item item: orderDetails.items){
                Node itemNode = graph.graphMap.get(item.longLat);
                if(!stopSegment.stores.contains(itemNode)){
                     stopSegment.stores.add(itemNode);
                 }
            }
            stopSegments.add(stopSegment);
        }
    }
}


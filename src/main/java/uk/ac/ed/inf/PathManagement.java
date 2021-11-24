package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class PathManagement {
    ArrayList<StopSegment> stopSegments = new ArrayList<>();
    ArrayList<Path> paths = new ArrayList<>();

    public PathManagement(Graph graph, Orders orders){
        generateStopSegments(graph, orders);
        makePaths(graph);
    }

    public void makePaths(Graph graph){
        for(StopSegment stopSegment: this.stopSegments){
            this.paths.add(stopSegment.bestPath(graph));
        }
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


package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class PathManagement {
    private static final LongLat APPLETON_TOWER = new LongLat(-3.186874, 55.944494);
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

    public Path findClosestPathStart(ArrayList<Path> unsortedPaths, Node node){
        //makes the assumption that paths.pathList.get(0) is always equal to paths.get(0).stops.get(0)
        //as the first stop in a path will always be equal to the first node - this is verified in pathManagementTest
        //todo actually verify the above lol
        double minDistance = node.distanceTo(unsortedPaths.get(0).stops.get(0));
        Path minDistancePath = unsortedPaths.get(0);
        int i = 0;
        for(Path path: unsortedPaths){
            Node candidateNode = path.stops.get(0);
            double candidateDistance = node.distanceTo(candidateNode);
            if(candidateDistance < minDistance && !node.equals(candidateNode)){
                minDistance = candidateDistance;
                minDistancePath = path;
                i++;
            }
        }
        unsortedPaths.remove(i);
        return minDistancePath;
    }

    private ArrayList<Path> sortPaths(Graph graph){
        LinkedList<Path> unsortedPaths = new LinkedList<>(this.paths);
        LinkedList<Path> sortedPaths = new LinkedList<Path>();
        sortedPaths.add((graph.graphMap.get(APPLETON_TOWER)));
        for(Path path: unsortedPaths){
            Node currentNode = sortedPaths.getLast().stops.;

        }

    }

    public Path greedyConcatAllPaths(Graph graph){
        ArrayList<Path> sortedPaths = sortPaths(graph);

        graph.nodeList;
    }
}


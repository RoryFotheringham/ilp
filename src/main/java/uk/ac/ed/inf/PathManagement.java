package uk.ac.ed.inf;


import java.util.ArrayList;
import java.util.LinkedList;

public class PathManagement {
    private static final LongLat APPLETON_TOWER_LONGLAT = new LongLat(-3.186874, 55.944494);
    ArrayList<StopSegment> stopSegments = new ArrayList<>();
    ArrayList<Path> paths = new ArrayList<>();
    Path absolutePath;

    public PathManagement(Graph graph, Orders orders){
        generateStopSegments(graph, orders);
        makePaths(graph);
        findAbsolutePath(graph);
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
                if(!stopSegment.stores.contains(itemNode)) {
                    stopSegment.stores.add(itemNode);
                }
            }
            stopSegments.add(stopSegment);
        }
    }

    public Path popClosestPathStart(LinkedList<Path> unsortedPaths, Node node){
        //makes the assumption that paths.pathLis.get(0) is always equal to paths.get(0).stops.get(0)
        //as the first stop in a path will always be equal to the first node - this is verified in pathManagementTest
        //todo actually verify the above lmao
        double minDistance = node.distanceTo(unsortedPaths.get(0).stops.get(0));
        Path minDistancePath = unsortedPaths.get(0);
        int minDistanceIndex = 0;
        int i = 0;
        for (Path path: unsortedPaths){
            Node candidateNode = path.stops.get(0);
            double candidateDistance = node.distanceTo(candidateNode);
            if(candidateDistance < minDistance && !node.equals(candidateNode)){
                minDistance = candidateDistance;
                minDistancePath = path;
                minDistanceIndex = i;
                i++;
            }
        }
        unsortedPaths.remove(minDistanceIndex);
        return minDistancePath;
    }

    public LinkedList<Path> sortPathsGreedy(Graph graph){
        LinkedList<Path> unsortedPaths = new LinkedList<>(this.paths);
        LinkedList<Path> sortedPaths = new LinkedList<>();
        ArrayList<Node> appletonPathList = new ArrayList<>();
        appletonPathList.add(graph.graphMap.get(APPLETON_TOWER_LONGLAT));
        Path appletonPath = new Path(appletonPathList, 0);
        appletonPath.addStops(appletonPathList);
        sortedPaths.add(appletonPath);
        for(int i = 0; i < this.paths.size(); i++){
            ArrayList<Node> stops = sortedPaths.getLast().stops;
            Node node = stops.get(stops.size() - 1);
            Path nextPath = popClosestPathStart(unsortedPaths, node);
            sortedPaths.add(nextPath);
        }
        sortedPaths.add(appletonPath);
        return sortedPaths;
    }

    private void findAbsolutePath(Graph graph){
        LinkedList<Path> sortedPaths = sortPathsGreedy(graph);
        while(sortedPaths.size() > 1){
            Path path_1 = sortedPaths.pop();
            Path path_2 = sortedPaths.pop();
            sortedPaths.push(path_1.concatPaths(graph, path_2));
        }
        this.absolutePath = sortedPaths.peek();
    }

}

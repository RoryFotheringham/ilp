package uk.ac.ed.inf;


import java.util.ArrayList;
import java.util.LinkedList;

public class PathManagement {
    private static final LongLat APPLETON_TOWER_LONGLAT = new LongLat(-3.186874, 55.944494);
    private static final String RETURN_ORDER_NO = "hometime";
    ArrayList<StopSegment> stopSegments = new ArrayList<>();
    ArrayList<Path> paths = new ArrayList<>();
    Path absolutePath;

    public PathManagement(Graph graph, Orders orders){
        generateStopSegments(graph, orders);
        makePaths(graph);
        findAbsolutePath(graph);

        if(!NoFlyPolice.validPathList(this.absolutePath.getPathList())){
            System.out.println("BAD PATH!!!");
        }
    }

    public Path getAbsolutePath() {
        return absolutePath;
    }

    public void makePaths(Graph graph){
        for(StopSegment stopSegment: this.stopSegments){
            this.paths.add(stopSegment.bestPath(graph));
        }
    }

    public void generateStopSegments(Graph graph, Orders orders){
        for(OrderDetails orderDetails: orders.getOrdersList()){
            StopSegment stopSegment = new StopSegment();
            Node destinationNode = graph.graphMapQuery(orderDetails.getDeliverTo());
            stopSegment.setDestination(destinationNode);
            stopSegment.setOrderNo(orderDetails.getOrderNo());
            for(Item item: orderDetails.getItems()){
                Node itemNode = graph.graphMapQuery(item.getLongLat());

                if(!stopSegment.stores.contains(itemNode)) {
                    stopSegment.stores.add(itemNode);
                }
            }
            stopSegments.add(stopSegment);
        }
    }

    public Path popClosestPathStart(LinkedList<Path> unsortedPaths, Node node){
        //makes the assumption that paths.pathLis.get(0) is always equal to paths.get(0).stops.get(0)
        //as the first stop in a path will always be equal to the first node
        double minDistance = node.distanceTo(unsortedPaths.get(0).getStops().get(0));
        Path minDistancePath = unsortedPaths.get(0);
        int minDistanceIndex = 0;
        int i = 0;
        for (Path path: unsortedPaths){
            Node candidateNode = path.getStops().get(0);
            double candidateDistance = node.distanceTo(candidateNode);
            if(candidateDistance < minDistance && !node.equals(candidateNode)){
                minDistance = candidateDistance;
                minDistancePath = path;
                minDistanceIndex = i;
            }
            i++;
        }
        unsortedPaths.remove(minDistanceIndex);
        if(!NoFlyPolice.validPathList(minDistancePath.getPathList())){
            System.out.println("BAD PATH!!!");
        }

        return minDistancePath;
    }

    public LinkedList<Path> sortPathsGreedy(Graph graph){
        LinkedList<Path> unsortedPaths = new LinkedList<>(this.paths);
        LinkedList<Path> sortedPaths = new LinkedList<>();
        ArrayList<Node> appletonPathList = new ArrayList<>();
        LinkedList<String> returnOrderNoList = new LinkedList<>();
        appletonPathList.add(graph.graphMapQuery(APPLETON_TOWER_LONGLAT));
        Path appletonPath = new Path(appletonPathList, 0);//adds appleton to start of path
        //note that the first appletonNode does not have a destination but the second 'appletonPathDestination' does.
        //this is because a node with a destination tells generateSubFlightPath to hover;
        appletonPath.setStops(appletonPathList);
        sortedPaths.add(appletonPath);
        for(int i = 0; i < this.paths.size(); i++){
            ArrayList<Node> stops = sortedPaths.getLast().getStops();
            Node node = stops.get(stops.size() - 1); //get final stop
            Path nextPath = popClosestPathStart(unsortedPaths, node);
            sortedPaths.add(nextPath);
        }
        Path appletonPathDestination = new Path(appletonPathList, 0);
        appletonPathDestination.setStops(appletonPathList);
        appletonPathDestination.setDestinations(new LinkedList<Node>(appletonPathList));
        returnOrderNoList.add(RETURN_ORDER_NO);
        appletonPathDestination.setOrderNos(returnOrderNoList);
        sortedPaths.add(appletonPathDestination);
        return sortedPaths;
    }

    private void findAbsolutePath(Graph graph){
        LinkedList<Path> sortedPaths = sortPathsGreedy(graph);
        while(sortedPaths.size() > 1){
            Path path_1 = sortedPaths.pop();
            Path path_2 = sortedPaths.pop();
            Path joinedPath = path_1.concatPaths(graph, path_2);
            sortedPaths.push(joinedPath);
        }
        this.absolutePath = sortedPaths.peek();
        this.absolutePath.getStops().remove(0);
    }

}

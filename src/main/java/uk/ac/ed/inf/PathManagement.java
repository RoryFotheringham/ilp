package uk.ac.ed.inf;


import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class handles functionality related to creating and sorting Paths.
 */
public class PathManagement {
    private static final LongLat APPLETON_TOWER_LONGLAT = new LongLat(-3.186874, 55.944494);
    private static final String RETURN_ORDER_NO = "hometime";
    ArrayList<StopSegment> stopSegments = new ArrayList<>();
    ArrayList<Path> paths = new ArrayList<>();
    Path absolutePath; // absolutePath is a Path which contains an ordered list of every node that needs to be visited in a journey
                       // as well as each stop, destination and orderNo.

    /**
     * When called the helper methods are called which create the ingredients required to find the absolute path.
     * Then it finds the absolute path.
     * @param graph a Graph
     * @param orders Orders containing every order that has been placed for the given date
     */
    public PathManagement(Graph graph, Orders orders){
        generateStopSegments(graph, orders);
        makePaths(graph);
        findAbsolutePath(graph);
    }

    public Path getAbsolutePath() {
        return absolutePath;
    }

    private void makePaths(Graph graph){
        for(StopSegment stopSegment: this.stopSegments){
            this.paths.add(stopSegment.bestPath(graph));
        }
    }

    /**
     * generates a list of StopSegments which represent the stops that need to reached in a given order
     * @param graph a Graph
     * @param orders all placed orders
     */
    private void generateStopSegments(Graph graph, Orders orders){
        for(OrderDetails orderDetails: orders.getOrdersList()){ //iterates through each placed order
            StopSegment stopSegment = new StopSegment();
            Node destinationNode = graph.graphMapQuery(orderDetails.getDeliverTo()); // finds the associated node for the destination of the order
            stopSegment.setDestination(destinationNode);
            stopSegment.setOrderNo(orderDetails.getOrderNo());
            for(Item item: orderDetails.getItems()){
                Node itemNode = graph.graphMapQuery(item.getLongLat()); // finds the associated node for each store that needs to be reached

                if(!stopSegment.stores.contains(itemNode)) { // prunes duplicate stores
                    stopSegment.stores.add(itemNode);
                }
            }
            stopSegments.add(stopSegment);
        }
    }

    /**
     * from a list of paths, pops the path whose start node is closest to a given node's destination node.
     * @param unsortedPaths list of paths yet to be considered
     * @param node the node for which the distances to each path's start node are being considered.
     * @return returns the closest path
     *
     * a possible improvement on this method might be to compare the distance of PathFind.findPath(from node to path.startnode) rather than the euclidean distance
     * because two nodes that are close might actually require a large amount of moves to reach as a result of the noFlyZone.
     */
    private Path popClosestPathStart(LinkedList<Path> unsortedPaths, Node node){
        //makes the assumption that paths.pathLis.get(0) is always equal to paths.get(0).stops.get(0)
        //as the first stop in a path will always be equal to the first node
        double minDistance = node.distanceTo(unsortedPaths.get(0).getStops().get(0));
        Path minDistancePath = unsortedPaths.get(0);
        int minDistanceIndex = 0;
        int i = 0;
        for (Path path: unsortedPaths){ // iterates through each path and updates the one that has the minimum distance
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

        return minDistancePath;
    }

    /**
     * method sorts th paths in this.paths according to the greedy method, starting with Appleton and proceeding to choose the next closest path.
     * @param graph a Graph
     * @return a list of paths that are sorted
     */
    private LinkedList<Path> sortPathsGreedy(Graph graph){
        LinkedList<Path> unsortedPaths = new LinkedList<>(this.paths);
        LinkedList<Path> sortedPaths = new LinkedList<>();
        ArrayList<Node> appletonPathList = new ArrayList<>();
        LinkedList<String> returnOrderNoList = new LinkedList<>();
        appletonPathList.add(graph.graphMapQuery(APPLETON_TOWER_LONGLAT));
        Path appletonPath = new Path(appletonPathList, 0);//adds appleton to start of path
        //note that the first appletonNode does not have a destination but the second 'appletonPathDestination' does.
        //this is because a node with a destination tells generateSubFlightPath to hover and the drone should not hover at start of its journey
        appletonPath.setStops(appletonPathList);
        sortedPaths.add(appletonPath);
        for(int i = 0; i < this.paths.size(); i++){
            ArrayList<Node> stops = sortedPaths.getLast().getStops();
            Node node = stops.get(stops.size() - 1); //get final stop
            Path nextPath = popClosestPathStart(unsortedPaths, node); //find the closest path with respect to the final stop
            sortedPaths.add(nextPath); // add this closest path to sorted paths.
        }
        Path appletonPathDestination = new Path(appletonPathList, 0);
        appletonPathDestination.setStops(appletonPathList);
        appletonPathDestination.setDestinations(new LinkedList<Node>(appletonPathList));
        returnOrderNoList.add(RETURN_ORDER_NO);
        appletonPathDestination.setOrderNos(returnOrderNoList);
        sortedPaths.add(appletonPathDestination);// appends AppletonPathDestination to sortedPaths
        return sortedPaths;
    }

    /**
     * takes a list of paths in order and concatenates them all into one big path
     * @param graph a Graph
     */
    private void findAbsolutePath(Graph graph){
        LinkedList<Path> sortedPaths = sortPathsGreedy(graph);
        while(sortedPaths.size() > 1){
            // concatenates the first element with the second.
            Path path_1 = sortedPaths.pop();
            Path path_2 = sortedPaths.pop();
            Path joinedPath = path_1.concatPaths(graph, path_2);
            // The result of this concatenation is then pushed to the front of the list
            // where it will be concatenated with the next element
            sortedPaths.push(joinedPath);
        }
        this.absolutePath = sortedPaths.peek();
        this.absolutePath.getStops().remove(0); // we remove the first stop (which is appleton tower) at this stage
        // this is because we need the stop in sortPathsGreedy to find the closest paths but if this stop is passed to the
        // flightPath algorithm then it will hover on its first move.
    }

}

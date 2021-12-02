package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * class handles implementation of A* path finding algorithm as explained in AI: A Modern Introduction by S. Russell and Peter Norvig.
 *  (Specifically, my reference for this algorithm is from Inf2D - Reasoning And Agents lectures)
 * The algorithm was first published by Peter Hart, Nils Nilsson and Bertram Raphael in 1968 from the Stanford Research Institute
 * For the implementation, I used this tutorial: https://stackabuse.com/graphs-in-java-a-star-algorithm/ as a reference.
 *  I did not copy the code exactly but my implementation was strongly influenced by it.
 */
public class PathFind {
    /**
     * high level static method creates a path whos pathList consists of the shortest path from a start node to a target node.
     * - the new pathList will contain the start and finish nodes.
     * @param graph a Graph
     * @param start the start node
     * @param target the node that must be reached
     * @param stops the stops that will be added to the new path
     * @param deliverTo the destination of the new path
     * @param orderNos the orderNumbers of the new path
     * @return a new Path object whos pathList consists of the shortest path from the start to the finish node.
     */
    public static Path findPath(Graph graph, Node start, Node target, ArrayList<Node> stops, LinkedList<Node> deliverTo, LinkedList<String> orderNos){
        graph.cleanNodes();
        Node node = aStar(start, target);
        return pathFromAStar(node, stops, deliverTo, orderNos);
    }

    /**
     * A* pathFinding algorithm finds the shortest path from start to finish
     * @param start start node
     * @param target target node
     * @return returns the final node in the sequence (the target node) or null if no path is possible.
     * The target node's attribute `parent` will be assigned which states where the node has come from if following the shortest route.
     * The parent node will also have a `parent` - this family tree can be followed until `parent == null` which must be the start node.
     */
    private static Node aStar(Node start, Node target) {
        PriorityQueue<Node> closedList = new PriorityQueue<>(); //contains nodes for whom all of their neighbours have been added to the open list
        PriorityQueue<Node> openList = new PriorityQueue<>(); //all of the nodes which are ready to be opened - on the frontier
        //initialise start node and add to the openList
        start.setG(0);
        start.setF(start.distanceTo(target));
        openList.add(start);

        while (!openList.isEmpty()) {
            Node n = openList.peek(); // get the next node in the openList
            if (n.equals(target)) {
                return n; // successful path has been found if n is the target
            }
            for (Edge edge : n.getEdges()) { // iterate through all of n's edges
                Node m = edge.getNode();
                double totalWeight = n.getG() + edge.getWeight(); // calculate the distance from start node to this node m

                if (!openList.contains(m) && !closedList.contains(m)) {
                    m.setParent(n);
                    m.setG(totalWeight);
                    m.setF(m.getG() + m.distanceTo(target));
                    openList.add(m); // if m is not in either list, ascribe it the appropriate values add it to the openList
                } else {
                    if (totalWeight < m.getG()) { // if this route to get to g is quicker than any other route...
                        m.setParent(n);
                        m.setG(totalWeight);
                        m.setF(m.getG() + m.distanceTo(target));
                        if (closedList.contains(m)) { // and it it is in the closedList, then get it in the openList with its new, improved values
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }
            openList.remove(n);
            closedList.add(n);
        }
        return null; // returns null if no path has been found
    }

    /**
     * method iterates through the 'family tree' of nodes in the shortest path and adds them to a pathList which is passed to a Path constructor
     * to make a new path with the pathList containing the shortest path
     * @param node the node which has been returned from the aStar algorithm
     * @param stops the stops to be added to the new path
     * @param deliverTo the destination to be added to the new path
     * @param orderNos the orderNos to be added to the new path
     * @return a path containing a pathList constructed from the 'family tree' of nodes as well as
     * the stops, destination and orderNos that have been passed as arguments
     */
    private static Path pathFromAStar(Node node, ArrayList<Node> stops, LinkedList<Node> deliverTo, LinkedList<String> orderNos){
        ArrayList<Node> pathList = new ArrayList<>();
        pathList.add(node);

        double totalWeight = node.getG();

        if(node.getParent() == null){
            Path path = new Path(pathList, node.getG());
            path.setStops(stops);
            path.setDestinations(deliverTo);
            path.setOrderNos(orderNos);
            return path;
        }

        while(!(node.getParent() == null)){
            pathList.add(node.getParent());
            node = node.getParent();
        }
        Collections.reverse(pathList);
        Path path = new Path(pathList, totalWeight);
        path.setStops(stops);
        path.setDestinations(deliverTo);
        path.setOrderNos(orderNos);
        return path;
    }
}


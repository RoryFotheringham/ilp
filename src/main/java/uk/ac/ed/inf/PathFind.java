package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class PathFind {
    public static Path findPath(Graph graph, Node start, Node target, ArrayList<Node> stops, LinkedList<Node> destinations, LinkedList<Node> stores){
        graph.cleanNodes();
        Node node = aStar(start, target);
        return pathFromAStar(node, stops, destinations, stores);
    }

    private static Node aStar(Node start, Node target) {
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        start.g = 0;
        start.f = start.distanceTo(target);
        openList.add(start);

        while (!openList.isEmpty()) {
            Node n = openList.peek();
            if (n.equals(target)) {
                return n;
            }
            for (Edge edge : n.edges) {
                Node m = edge.node;
                double totalWeight = n.g + edge.weight;

                if (!openList.contains(m) && !closedList.contains(m)) {
                    m.parent = n;
                    m.g = totalWeight;
                    m.f = m.g + m.distanceTo(target);
                    openList.add(m);
                } else {
                    if (totalWeight < m.g) {
                        m.parent = n;
                        m.g = totalWeight;
                        m.f = m.g + m.distanceTo(target);
                        if (closedList.contains(m)) {
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }
            openList.remove(n);
            closedList.add(n);
        }
        return null;
    }

    private static Path pathFromAStar(Node node, ArrayList<Node> stops, LinkedList<Node> stores, LinkedList<Node> destinations){
        ArrayList<Node> pathList = new ArrayList<>();
        pathList.add(node);

        double totalWeight = node.g;

        if(node.parent == null){
            Path path = new Path(pathList, node.g);
            path.setStops(stops);
            path.setDestinations(destinations);
            path.setStores(stores);
            return path;
        }

        while(!(node.parent == null)){
            pathList.add(node.parent);
            node = node.parent;
        }
        Collections.reverse(pathList);
        Path path = new Path(pathList, totalWeight);
        path.setStops(stops);
        path.setDestinations(destinations);
        path.setStores(stores);
        return path;
    }
}


package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class PathFind {
    public static Path findPath(Graph graph, Node start, Node target, ArrayList<Node> stops, LinkedList<Node> deliverTo, LinkedList<String> orderNos){
        graph.cleanNodes();
        Node node = aStar(start, target);
        return pathFromAStar(node, stops, deliverTo, orderNos);
    }

    private static Node aStar(Node start, Node target) {
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        start.setG(0);
        start.setF(start.distanceTo(target));
        openList.add(start);

        while (!openList.isEmpty()) {
            Node n = openList.peek();
            if (n.equals(target)) {
                return n;
            }
            for (Edge edge : n.getEdges()) {
                Node m = edge.node;
                double totalWeight = n.getG() + edge.weight;

                if (!openList.contains(m) && !closedList.contains(m)) {
                    m.setParent(n);
                    m.setG(totalWeight);
                    m.setF(m.getG() + m.distanceTo(target));
                    openList.add(m);
                } else {
                    if (totalWeight < m.getG()) {
                        m.setParent(n);
                        m.setG(totalWeight);
                        m.setF(m.getG() + m.distanceTo(target));
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


package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class PathFind {
    public static Path findPath(Graph graph, Node start, Node target){
        graph.cleanNodes();
        Node node = aStar(start, target);
        return pathFromAStar(node);
    }

    public static Node aStar(Node start, Node target) {
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

    public static Path pathFromAStar(Node node){
        ArrayList<Node> pathList = new ArrayList<>();
        pathList.add(node);
        Path path = new Path(pathList, node.g);

        if(node.parent == null){
            System.out.println("Path of length zero generated");
            return path;
        }

        while(!(node.parent == null)){
            pathList.add(node.parent);
            node = node.parent;
        }
        return path;
    }
}


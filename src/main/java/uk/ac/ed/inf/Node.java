package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.Objects;

public class Node implements Comparable<Node>{
    private Node parent;
    private ArrayList<Edge> edges = new ArrayList<>();
    private LongLat longLat;
    private double f = Double.MAX_VALUE; /*total cost of a node (heuristic distance to target + actual distance from start)
                                          *this is in accordance with the A* path finding algorithm
                                          */
    private double g = Double.MAX_VALUE; // total distance to this node from start node using current shortest path
    private String name;

    public Node(ArrayList<Edge> edges, LongLat longLat) {
        this.edges = edges;
        this.longLat = longLat;
    }

    public Node(LongLat longLat){
        this.longLat = longLat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public LongLat getLongLat() {
        return longLat;
    }

    /**
     * refreshes a node's f and g value which are used in the FindPath.pathfind method
     * and must be set to infinity for the algorithm to work
     */
    public void cleanNode(){
        this.setF(Double.POSITIVE_INFINITY);
        this.setG(Double.POSITIVE_INFINITY);
        this.setParent(null);
    }

    /**
     * overriding equals as Node objects will be compared in the context of
     * querying Graph.graphMap which is a HashMap<LongLat, Node>
     * @param o object
     * @return true if objects have the same longitude and latitude coords
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(longLat, node.longLat);
    }

    /**
     * HashCode override to maintain the hashcode contract given the override of equals
     * - that two equal objects must give the same hashCode
     * - This is particularly relevant as LongLat is used in a HashMap in the Graph class
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(longLat);
    }

    /**
     * override compareTo for use in the PathFind.findpath algorithm which compares the f values of the node
     * @param node
     * @return
     */
    @Override
    public int compareTo(Node node) {
        return Double.compare(this.getF(), node.getF());
    }

    /**
     * distance between two nodes is the distance between their LongLat points
     * @param node node to which this node is being compared
     * @return returns the euclidean distance between the two nodes
     */
    public double distanceTo(Node node){
    double distance;
    LongLat p1 = this.longLat;
    LongLat p2 = node.longLat;
    distance = p1.distanceTo(p2);
    return distance;
    }

    public Node getParent() {
        return parent;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }
}


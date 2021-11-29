package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.Objects;

public class Node implements Comparable<Node>{
    private Node parent;
    private ArrayList<Edge> edges = new ArrayList<>();
    private LongLat longLat;
    private double f = Double.MAX_VALUE;
    private double g = Double.MAX_VALUE;
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

    public void cleanNode(){
        this.setF(Double.POSITIVE_INFINITY);
        this.setG(Double.POSITIVE_INFINITY);
        this.setParent(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(longLat, node.longLat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longLat);
    }

    @Override
    public int compareTo(Node node) {
        return Double.compare(this.getF(), node.getF());
    }

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


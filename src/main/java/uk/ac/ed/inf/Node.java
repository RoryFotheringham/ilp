package uk.ac.ed.inf;

import java.util.ArrayList;

public class Node{
    Node parent;
    ArrayList<Edge> edges = new ArrayList<>();
    LongLat longLat;

    public Node(ArrayList<Edge> edges, LongLat longLat) {
        this.edges = edges;
        this.longLat = longLat;
    }

    public Node(LongLat longLat){
        this.longLat = longLat;
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

    public double distanceTo(Node node){
    double distance;
    LongLat p1 = this.longLat;
    LongLat p2 = node.longLat;
    distance = p1.distanceTo(p2);
    return distance;
    }
}
/*
public class Node {
    Node parent;
    ArrayList<Edge> edges = new ArrayList<Edge>();
    Grid index;

    public Node(ArrayList<Edge> edges, Grid index){
        this.edges = edges;
        this.index = index;
    }

    public Node(Grid index){
        this.index = index;
    }

    public void addEdge(Edge edge){
        this.edges.add(edge);
    }
}
*/
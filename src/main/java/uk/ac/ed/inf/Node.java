package uk.ac.ed.inf;

import java.util.ArrayList;

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

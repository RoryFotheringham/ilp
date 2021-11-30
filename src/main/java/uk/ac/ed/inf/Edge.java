package uk.ac.ed.inf;

/**
 * an edge object that is stored in a node
 */
public class Edge {
    private double weight = 0;
    private Node node;

    public Edge(double weight, Node node){
        this.weight = weight;
        this.node = node;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
